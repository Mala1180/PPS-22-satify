import com.tngtech.archunit.core.domain.{JavaClass, JavaClasses}
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.{ArchCondition, ArchRule, EvaluationResult, SimpleConditionEvent}
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.{classes, noClasses}
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.Architecture.MVU
import satify.Main.Model
import satify.update.Message
import satify.view.GUI

class TestArchitecture extends AnyFlatSpec with Matchers:

  val mvu: MVU = new MVU {}
  val RootPackage: String = "satify"
  val ModelPackage: String = RootPackage + ".model"
  val UpdatePackage: String = RootPackage + ".update"
  val ViewPackage: String = RootPackage + ".view"

  val allClasses: JavaClasses = ClassFileImporter().importPackages(RootPackage)

  "Architecture" should "not have cyclic dependencies" in {
    val noCycles = slices()
      .matching(RootPackage + ".(*)..")
      .should()
      .beFreeOfCycles()

    noCycles.check(allClasses)
  }

  "Model" should "be immutable" in {
    val immutabilityRule = classes()
      .that()
      .resideInAPackage(ModelPackage)
      .should()
      .haveOnlyFinalFields
    immutabilityRule.check(allClasses)
  }

  "Model" should "not depend on View and Update" in {
    val rule = noClasses()
      .that()
      .resideInAPackage(ModelPackage)
      .should()
      .dependOnClassesThat
      .resideInAPackage(ViewPackage)
      .orShould()
      .dependOnClassesThat
      .resideInAPackage(UpdatePackage)
    rule.check(allClasses)
  }

  "View" should "be a function which takes a Model and returns a GUI" in {
    mvu.view shouldBe a[Model => GUI]
  }

  "View" should "depend on Model" in {
    val rule = classes()
      .that()
      .resideInAPackage(ViewPackage)
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage(ModelPackage)

    rule.check(allClasses)
  }

  "Update" should "be a function taking a Model and a Message, returning a new Model" in {
    mvu.update shouldBe a [(Model, Message) => Model]
  }

  "Update" should "depend on Model" in {
    val rule = classes()
      .that()
      .resideInAPackage(UpdatePackage)
      .should()
      .dependOnClassesThat()
      .resideInAnyPackage(ModelPackage)
    rule.check(allClasses)
  }

