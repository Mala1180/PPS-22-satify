package satify

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.{ClassFileImporter, ImportOption}
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.{classes, noClasses}
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import satify.Architecture.MVU
import satify.Main.Model
import satify.update.Message
import satify.view.GUI

import scala.swing.Component

class TestArchitecture extends AnyFlatSpec with Matchers:

  val mvu: MVU = new MVU {}
  val RootPackage: String = "satify"
  val ModelPackage: String = RootPackage + ".model"
  val UpdatePackage: String = RootPackage + ".update"
  val ViewPackage: String = RootPackage + ".view"

  val allClasses: JavaClasses = ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS).importPackages(RootPackage)

  "Architecture" should "not have cyclic dependencies" in {
    val noCycles: ArchRule = slices()
      .matching(RootPackage + ".(*)..")
      .should()
      .beFreeOfCycles()

    noCycles.check(allClasses)
  }

  "Model" should "be immutable" in {
    val immutability: ArchRule = classes()
      .that()
      .resideInAPackage(ModelPackage)
      .should()
      .haveOnlyFinalFields
    immutability.check(allClasses)
  }

  "Model" should "not depend on View and Update" in {
    val independence: ArchRule = noClasses()
      .that()
      .resideInAPackage(ModelPackage)
      .should()
      .dependOnClassesThat
      .resideInAPackage(ViewPackage)
      .orShould()
      .dependOnClassesThat
      .resideInAPackage(UpdatePackage)
    independence.check(allClasses)
  }

  "View" should "be a function which takes a Model and returns a set of components" in {
    mvu.view shouldBe a[Model => Set[Component]]
  }

  "Update" should "be a function taking a Model and a Message, returning a new Model" in {
    mvu.update shouldBe a[(Model, Message) => Model]
  }
