import Architecture.{MVU, UpdateComponent}
import Main.Model
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.{classes, noClasses}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import update.Message
import view.GUI

class TestArchitecture extends AnyFlatSpec with Matchers:

  val mvu: MVU = new MVU {}

  val modelClasses: JavaClasses = ClassFileImporter().importPackages("model")
  val viewClasses: JavaClasses = ClassFileImporter().importPackages("view")
  val updateClasses: JavaClasses = ClassFileImporter().importPackages("update")

  "Model" should "be immutable" in {
    val modelRule = classes()
      .that()
      .resideInAPackage("model")
      .should()
      .haveOnlyFinalFields
    modelRule.check(modelClasses)
  }

  "Model" should "not depend on View and Update" in {
    val modelRule = classes()
      .that()
      .resideInAPackage("model")
      .should()
      .haveOnlyFinalFields
    modelRule.check(modelClasses)


    //    val modelRule = ArchRuleDefinition.noClasses.that
    //      .resideInAPackage(BASE_PACKAGE + ".model..")
    //      .should
    //      .dependOnClassesThat
    //      .resideInAPackage(BASE_PACKAGE + ".view..")
    //      .orShould
    //      .dependOnClassesThat
    //      .resideInAPackage(BASE_PACKAGE + ".update..")
    //
    //    modelRule.check(importedClasses)
    //    val classes = new ClassFileImporter().importPackagesOf(classOf[UpdateComponent])
    //
    //    noClasses()
    //      .that()
    //      .haveSimpleName("SomeExample")
    //      .should()
    //      .dependOnClassesThat()
    //      .haveSimpleName("Other")
    //      .check(classes)
  }

  "Update" should "be a function taking a Model and a Message, returning a new Model" in {
    mvu.update shouldBe a[(Model, Message) => Model]
  }

  "View" should "be a function which takes a Model and returns a GUI" in {
    mvu.view shouldBe a[Model => GUI]
  }
