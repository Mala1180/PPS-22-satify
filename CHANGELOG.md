# CHANGELOG

## [v0.2.0](https://github.com/Mala1180/PPS-22-satify/releases/tag/v0.2.0) - 2023-08-28 16:51:56

Prototype released at the end of the third Sprint.
It permits solving simple SAT problem instances and provides a richer DSL for the input. 

## What's Changed
* Align develop with main after release by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/35
* Feature/dpll-unit-propagation by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/38
* Feature/backlog-sprint-3 by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/36
* Feature/sbt-assembly by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/37
* Feature/auto-release by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/41
* Feature/dsl-math-operators by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/42
* Feature/complete-model by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/44
* Feature/cnf-output by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/43
* Feature/gui-refactor by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/45
* Feature/dpll-pure-literals-elimination by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/39
* Feature/cnf-dimacs-parse by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/46
* Feature/import-dimacs by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/48
* Feature/sat-encodings by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/49
* Feature/dpll-output by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/50
* Feature/dsl-implications by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/51
* Feature/tseitin-transformations-fix by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/53
* Feature/fix-dsl-reflection by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/55
* Feature/documentation-improvement by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/54
* chore: replace scoverage tool with jacoco by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/47
* Feature/problems-nqueens by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/52
* fix: import reflection by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/56
* Release/0.2.0 by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/57


**Full Changelog**: https://github.com/Mala1180/PPS-22-satify/compare/v0.1.0...v0.2.0

### Feature 🚀

- general:
  - introduce reflection tests, test build with fork ([695fd56](https://github.com/Mala1180/PPS-22-satify/commit/695fd5617ca1daa02ac8e10785e7b5c91439430a)) ([#55](https://github.com/Mala1180/PPS-22-satify/pull/55))
  - add Reflection object in dsl, get operators from dsl dynamically ([a0a9cb2](https://github.com/Mala1180/PPS-22-satify/commit/a0a9cb2226e3013bebd1599a8fd5ce0b23ec9c06)) ([#51](https://github.com/Mala1180/PPS-22-satify/pull/51))
  - add implies and iff operators ([eae2f9c](https://github.com/Mala1180/PPS-22-satify/commit/eae2f9caf53bf31552e45ee2da0e634f8e53d4b5)) ([#51](https://github.com/Mala1180/PPS-22-satify/pull/51))
  - add wrong use test for sat encoding, refactor some methods ([3fe2c47](https://github.com/Mala1180/PPS-22-satify/commit/3fe2c47732355393e12e67dee03c6f667a30a156)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - add atMost one, atLeast one and atLEast k ([f8d2b94](https://github.com/Mala1180/PPS-22-satify/commit/f8d2b946bfabbce02917b6ce95609003233b7bc8)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - add print method for Expression ([c032a1d](https://github.com/Mala1180/PPS-22-satify/commit/c032a1d623a521f35cc34f8e524e17b965314177)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - add atLeastOne and atMostOne encodings ([fe7956d](https://github.com/Mala1180/PPS-22-satify/commit/fe7956d471040d2af9dc8d5dc140d0b2bc38c4f3)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - define factories of State ([e791d42](https://github.com/Mala1180/PPS-22-satify/commit/e791d4236c0ca56f07ddbbb8fd1f4510e868e3de)) ([#44](https://github.com/Mala1180/PPS-22-satify/pull/44))
  - add fields to State, change xor symbol ([86ca9ea](https://github.com/Mala1180/PPS-22-satify/commit/86ca9ea55a1a6d9b91b05ad380125fe809c37087)) ([#44](https://github.com/Mala1180/PPS-22-satify/pull/44))
  - add xor operator ([9937306](https://github.com/Mala1180/PPS-22-satify/commit/99373062f49e0644355f3e36d4d6ffb57648af36)) ([#42](https://github.com/Mala1180/PPS-22-satify/pull/42))
  - add basic math operators ([02e302a](https://github.com/Mala1180/PPS-22-satify/commit/02e302a242fc475bdb8be2622ad5b14ce30cb0b7)) ([#42](https://github.com/Mala1180/PPS-22-satify/pull/42))
  - comment ([368407f](https://github.com/Mala1180/PPS-22-satify/commit/368407f9380605ffcdb580920c37012b85bf2c50)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - add sbt-assembly, fixed image in jar, modify README ([d8d7339](https://github.com/Mala1180/PPS-22-satify/commit/d8d7339d9052d7c5d5cc008f0fb90c8e48618a47)) ([#37](https://github.com/Mala1180/PPS-22-satify/pull/37))

### Bug Fixes 🐛

- general:
  - fix import reflection ([e851185](https://github.com/Mala1180/PPS-22-satify/commit/e8511856cc69ac39a00eeb2e24f393c0718c8558)) ([#56](https://github.com/Mala1180/PPS-22-satify/pull/56))
  - solve conflicts ([d51957e](https://github.com/Mala1180/PPS-22-satify/commit/d51957eb7112c8ca5396e1840b18fa85916c86a5)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - dsl improvement with RegExp input processing ([b8e67d1](https://github.com/Mala1180/PPS-22-satify/commit/b8e67d1904cf91e8fec866326f5a09918648690f)) ([#55](https://github.com/Mala1180/PPS-22-satify/pull/55))
  - tseitin tests and trasformations, removed println ([42b7174](https://github.com/Mala1180/PPS-22-satify/commit/42b7174be78a21da86e85644a7a098d94dec7ff3)) ([#53](https://github.com/Mala1180/PPS-22-satify/pull/53))
  - transformation ([973d799](https://github.com/Mala1180/PPS-22-satify/commit/973d79982380169a3ff5ae0bb3e11b7e981328ac)) ([#53](https://github.com/Mala1180/PPS-22-satify/pull/53))
  - merge conflicts ([bba99ee](https://github.com/Mala1180/PPS-22-satify/commit/bba99ee805753f87e9f3e4235e85988bce85ee39)) ([#50](https://github.com/Mala1180/PPS-22-satify/pull/50))
  - remove smtlib ([302fd99](https://github.com/Mala1180/PPS-22-satify/commit/302fd99e9fdfa86f5a38b07b433a81beecf117f1)) ([#46](https://github.com/Mala1180/PPS-22-satify/pull/46))
  - merge conflicts ([88ee6c1](https://github.com/Mala1180/PPS-22-satify/commit/88ee6c1b932a59cb95d5bc19054927b5b2181db2)) ([#46](https://github.com/Mala1180/PPS-22-satify/pull/46))
  - given import ([8371593](https://github.com/Mala1180/PPS-22-satify/commit/83715936b83d4ac165f932d17b75df4bd8b0966d)) ([#39](https://github.com/Mala1180/PPS-22-satify/pull/39))
  - print method ([cd74491](https://github.com/Mala1180/PPS-22-satify/commit/cd744911197c11212f9d0e122acdfbdf82c29a49)) ([#43](https://github.com/Mala1180/PPS-22-satify/pull/43))
  - fix for architecture test ([dc64cbb](https://github.com/Mala1180/PPS-22-satify/commit/dc64cbb47a9c7cb913e986688910a90596994fd0)) ([#43](https://github.com/Mala1180/PPS-22-satify/pull/43))
  - removed useless file ([311c9d6](https://github.com/Mala1180/PPS-22-satify/commit/311c9d6942487c54380a63c3695055a0d2623ca4)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - trim input in pre processing ([3643503](https://github.com/Mala1180/PPS-22-satify/commit/3643503fdb22eff06e553b6e5c85e8aee9a5a497)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - fmt ([e82a418](https://github.com/Mala1180/PPS-22-satify/commit/e82a41843f8215c740e61dfae75239808125a005)) ([#39](https://github.com/Mala1180/PPS-22-satify/pull/39))
  - cnf in pure lit elimination test ([e034a1e](https://github.com/Mala1180/PPS-22-satify/commit/e034a1ece14a72ac620637ff11a1baa8a1704149)) ([#39](https://github.com/Mala1180/PPS-22-satify/pull/39))
  - PartialModelUtilsTest, format code ([c3d7c30](https://github.com/Mala1180/PPS-22-satify/commit/c3d7c30df61b30dfab9bdaac2a3c82391af94bd9)) ([#38](https://github.com/Mala1180/PPS-22-satify/pull/38))

### Documentation 📚

- general:
  - finished backlog ([9d6ee59](https://github.com/Mala1180/PPS-22-satify/commit/9d6ee59b362db31a2dfe2db5177f59ab898151e9)) ([#57](https://github.com/Mala1180/PPS-22-satify/pull/57))
  - add some doc ([c306729](https://github.com/Mala1180/PPS-22-satify/commit/c30672903942b307f44920c39dbdfdfc9608e5cc)) ([#51](https://github.com/Mala1180/PPS-22-satify/pull/51))
  - add changelog ([7a5bd34](https://github.com/Mala1180/PPS-22-satify/commit/7a5bd3436241968d7b1ea7257b9a4a180b1f6961)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))

### Refactor 🛠️

- general:
  - modify atMostOne ([22386e5](https://github.com/Mala1180/PPS-22-satify/commit/22386e56cc270a36ca535366c088044446791ab0)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - move encodings to expression package ([7b5a5d4](https://github.com/Mala1180/PPS-22-satify/commit/7b5a5d41b45bc659f0e70845cab01d4968309dc6)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - move dsl package outside model ([9b192c8](https://github.com/Mala1180/PPS-22-satify/commit/9b192c88e0ac805b0c932e9e90692460beaf891d)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - add given conversion for tuple, modify extesion methods atMost and atLeast ([fa7684e](https://github.com/Mala1180/PPS-22-satify/commit/fa7684ea818cb1c940cb29b23a1822e1390957d8)) ([#55](https://github.com/Mala1180/PPS-22-satify/pull/55))
  - undo Example-> Problem ([73b884d](https://github.com/Mala1180/PPS-22-satify/commit/73b884d8eb5512481265832f19a0cc85b67dcb11)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - move NQueens files ([ba35e6f](https://github.com/Mala1180/PPS-22-satify/commit/ba35e6fe999780a66b422ce0f3b0b3c5a8c41774)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - file refactor ([e5f0a02](https://github.com/Mala1180/PPS-22-satify/commit/e5f0a023ef82dd11c6dea705bff0bf68d06240ae)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - move OrderedSeq's given, delete wrong import, rename pure literals elimination methods ([08e5a24](https://github.com/Mala1180/PPS-22-satify/commit/08e5a24fe3895377ae6816aff11a8e37d11abaad)) ([#39](https://github.com/Mala1180/PPS-22-satify/pull/39))
  - refactor gui, view architecture and remove useless print ([3ffe6b7](https://github.com/Mala1180/PPS-22-satify/commit/3ffe6b79baded4b5755d71aa4064e194a7928190)) ([#45](https://github.com/Mala1180/PPS-22-satify/pull/45))
  - move extractSolutionsFromDT method ([905b878](https://github.com/Mala1180/PPS-22-satify/commit/905b87819daacb6d13b75e6c18f9f14c2e07594a)) ([#38](https://github.com/Mala1180/PPS-22-satify/pull/38))
  - rename Constant -> Bool ([ce4ebc2](https://github.com/Mala1180/PPS-22-satify/commit/ce4ebc2362f3780d1f1a72b5781e8a6f18c1acfb)) ([#38](https://github.com/Mala1180/PPS-22-satify/pull/38))

### Style Improvements 💉

- general:
  - format ([ad951f7](https://github.com/Mala1180/PPS-22-satify/commit/ad951f7b25c749266a74516decccadcacab7b214)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - remove white space ([3f7234d](https://github.com/Mala1180/PPS-22-satify/commit/3f7234d7aef1a432105fc18f3943d002e9725257)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - remove useless white spaces ([baf8c5b](https://github.com/Mala1180/PPS-22-satify/commit/baf8c5bf95b6b5511fa5a9e9980d91b7a29a53c5)) ([#53](https://github.com/Mala1180/PPS-22-satify/pull/53))
  - remove useless white spaces ([31d1684](https://github.com/Mala1180/PPS-22-satify/commit/31d168479c434aebf05e20f45cbe2002226e522b)) ([#53](https://github.com/Mala1180/PPS-22-satify/pull/53))
  - format ([a8c49f2](https://github.com/Mala1180/PPS-22-satify/commit/a8c49f2bcf1eed74a3fc5c4b30b5e4422a8d37c9)) ([#51](https://github.com/Mala1180/PPS-22-satify/pull/51))
  - format ([11ef43d](https://github.com/Mala1180/PPS-22-satify/commit/11ef43dca4aee9c58d7106d93171ec7afba38e26)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - format ([12e6fe8](https://github.com/Mala1180/PPS-22-satify/commit/12e6fe8d601be91eacd7e4fd8c8895935ddd38cd)) ([#42](https://github.com/Mala1180/PPS-22-satify/pull/42))

### Work in progress ⚙️

- general:
  - test import option for exclude instrumented-classes ([0abb83a](https://github.com/Mala1180/PPS-22-satify/commit/0abb83a1007f990787bae3ece8808937e774da4b)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - test import option on ClassFileImporter ([4dda102](https://github.com/Mala1180/PPS-22-satify/commit/4dda1021139cfc99c8eafb968736eecd811aee71)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - add first Feature and scenarion for Tseitin Transformation ([38294dc](https://github.com/Mala1180/PPS-22-satify/commit/38294dc6a1f5a533ae4b6f2c797651f43f2d3706)) ([#53](https://github.com/Mala1180/PPS-22-satify/pull/53))
  - add method for Exp -> CNF conversion if allowable ([4d3a3d2](https://github.com/Mala1180/PPS-22-satify/commit/4d3a3d2869fcdf1e6ac28d9eb69c62344b079979)) ([#53](https://github.com/Mala1180/PPS-22-satify/pull/53))
  - work on import from DIMACS format ([727db5b](https://github.com/Mala1180/PPS-22-satify/commit/727db5b21d81ee776833b9ca9df489982dbd620b)) ([#48](https://github.com/Mala1180/PPS-22-satify/pull/48))
  - first improve to doc ([6a77ecb](https://github.com/Mala1180/PPS-22-satify/commit/6a77ecb05991ed3fb206316d6380058331d8bdae)) ([#54](https://github.com/Mala1180/PPS-22-satify/pull/54))

### Continuous Integration 🤖️

- general:
  - replace test branch with main ([75a6177](https://github.com/Mala1180/PPS-22-satify/commit/75a6177e247bcf5787b8f8eb6bf4592488f69feb)) ([#57](https://github.com/Mala1180/PPS-22-satify/pull/57))
  - restored ci file ([f6fbbe8](https://github.com/Mala1180/PPS-22-satify/commit/f6fbbe8b57e9b2606ca3dcb9d2abd46ce32210b4)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - test remove target folder ([c73c244](https://github.com/Mala1180/PPS-22-satify/commit/c73c2447bbdf4dad28adab58d9dfef8fe66c3f90)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - test remove target folder ([2414edf](https://github.com/Mala1180/PPS-22-satify/commit/2414edf7276d1defa2d1fa52028633e9484551b6)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - test remove target folder ([27ee416](https://github.com/Mala1180/PPS-22-satify/commit/27ee4160ec8beddce502b895011e37b1aae636ca)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - test remove target folder ([f464d24](https://github.com/Mala1180/PPS-22-satify/commit/f464d240d60a02b57d2cd6675d77e953a6589752)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - test rm target ([5e1d332](https://github.com/Mala1180/PPS-22-satify/commit/5e1d33276fc08c16fa3a39c2f53a095b2e317378)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - test remove Target folder to prevent crash of workflow ([b23447a](https://github.com/Mala1180/PPS-22-satify/commit/b23447aff410e6f1b98000363070cc20ac98efea)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - testing remove cache:sbt ([7231387](https://github.com/Mala1180/PPS-22-satify/commit/7231387a26a808161d0a664f8bd2a3d7301ec699)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - update ci release ([beec700](https://github.com/Mala1180/PPS-22-satify/commit/beec700dad311225606f25aede1a49df21c1185f)) ([#40](https://github.com/Mala1180/PPS-22-satify/pull/40))
  - fix branch list ([0e837ff](https://github.com/Mala1180/PPS-22-satify/commit/0e837ff9a5493633d9bc89bfced427c61774e667)) ([#40](https://github.com/Mala1180/PPS-22-satify/pull/40))
  - add comment in ci ([329f39d](https://github.com/Mala1180/PPS-22-satify/commit/329f39dd7dc74090a7f7784e77a69d14ace4b5c5)) ([#40](https://github.com/Mala1180/PPS-22-satify/pull/40))
  - modify ci for branch test ([42860e4](https://github.com/Mala1180/PPS-22-satify/commit/42860e48cb14a6be5b5095d35982c4982f00089a)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - add fallback version ([03245bb](https://github.com/Mala1180/PPS-22-satify/commit/03245bb4f15b4c0e0141df6067824bed78c1fda1)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - skip on empty ([198ae23](https://github.com/Mala1180/PPS-22-satify/commit/198ae23e7b5f998a253a79ad3d8a505b7a08cb66)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - checkout code for changelog ([bdf0e99](https://github.com/Mala1180/PPS-22-satify/commit/bdf0e994233ec8003499e4a5f1ee305b06268888)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - add workflows for releases ([462c60c](https://github.com/Mala1180/PPS-22-satify/commit/462c60c02033ada83aca6df3d8af74058b0ca077)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))

### Chores 🖋️

- general:
  - remove comment ([5cee6da](https://github.com/Mala1180/PPS-22-satify/commit/5cee6daf1ad219edca76f7f93d6213909fa3fc99)) ([#56](https://github.com/Mala1180/PPS-22-satify/pull/56))
  - remove reflection tests for sbt problem ([abc9e95](https://github.com/Mala1180/PPS-22-satify/commit/abc9e95cf092f0264640c2f764953405cb80d87e)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - integrate example into Problem ([9a16f8d](https://github.com/Mala1180/PPS-22-satify/commit/9a16f8d03837fb917c012bde4436d5f327f0fdd3)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - dimacs file follows standard, edit NQueens print, add NQueens 10x10 ([a12e810](https://github.com/Mala1180/PPS-22-satify/commit/a12e810880baf5b5604d01e69fc4ff274ddf8247)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - add tseitin variable filter on extract solutions ([d6e16d0](https://github.com/Mala1180/PPS-22-satify/commit/d6e16d02339e70328ae70f4e549fbcebfc479e35)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - add NQueens 3x3 ([94c7216](https://github.com/Mala1180/PPS-22-satify/commit/94c7216cd3e02a800f9ed09c91e13d2d39a972e6)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - DIMACS variables in lexicographically sortable, NQueens terminal print ([5d2def4](https://github.com/Mala1180/PPS-22-satify/commit/5d2def416307fc18b0b3d70fe1357c63e782b7a2)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - add NQueens example ([04fee2b](https://github.com/Mala1180/PPS-22-satify/commit/04fee2bd10199d47034b6b5f989db566bd98f689)) ([#52](https://github.com/Mala1180/PPS-22-satify/pull/52))
  - change name to operators tests ([1ea9977](https://github.com/Mala1180/PPS-22-satify/commit/1ea99772652dd8878863a91a354ac87242c16619)) ([#51](https://github.com/Mala1180/PPS-22-satify/pull/51))
  - add isCNF and conversion exp -> CNF methods ([cbab8e4](https://github.com/Mala1180/PPS-22-satify/commit/cbab8e45bd123719dc8c17dc8f4ae627f335b289)) ([#53](https://github.com/Mala1180/PPS-22-satify/pull/53))
  - change print methods name and remove some spaces in OutputTest ([6d7fa10](https://github.com/Mala1180/PPS-22-satify/commit/6d7fa103916550f3a4a838864a9f7451b9bb7425)) ([#48](https://github.com/Mala1180/PPS-22-satify/pull/48))
  - remove print from Expression ([af8a8b2](https://github.com/Mala1180/PPS-22-satify/commit/af8a8b29713c0dcf4a19e707cdc37ab073af4cef)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - add dpll GUI output ([db54a2c](https://github.com/Mala1180/PPS-22-satify/commit/db54a2cb1418a20dfbf44d2c1d730e6de6b13106)) ([#50](https://github.com/Mala1180/PPS-22-satify/pull/50))
  - integrate dpll into the View ([db50620](https://github.com/Mala1180/PPS-22-satify/commit/db50620d9bbe9d938b676a8a20d110f4a24de891)) ([#50](https://github.com/Mala1180/PPS-22-satify/pull/50))
  - spaced ([c05f508](https://github.com/Mala1180/PPS-22-satify/commit/c05f508445c9f84da0047f1a2a840020b50d5dad)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - introduce lazy val ([03fa314](https://github.com/Mala1180/PPS-22-satify/commit/03fa3140eb308d46d010720141a68cb428a4e879)) ([#50](https://github.com/Mala1180/PPS-22-satify/pull/50))
  - delete comment and minor change to doc ([859dcbe](https://github.com/Mala1180/PPS-22-satify/commit/859dcbee64457caea1dfcea7a2e3e5a5e85872e4)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - add type annotation to symbolGenerator in atMostOne ([bc8bee5](https://github.com/Mala1180/PPS-22-satify/commit/bc8bee5541296b3522a4ac31476f5b5657dbf23f)) ([#49](https://github.com/Mala1180/PPS-22-satify/pull/49))
  - DIMACS parse function ([c5cf6fd](https://github.com/Mala1180/PPS-22-satify/commit/c5cf6fdc19d9e530ac3c9358fe156704d56e0ff2)) ([#46](https://github.com/Mala1180/PPS-22-satify/pull/46))
  - replace scoverage tool with jacoco ([ec8a3cd](https://github.com/Mala1180/PPS-22-satify/commit/ec8a3cd40914664afcbcbe384a8c29dffb4db347)) ([#47](https://github.com/Mala1180/PPS-22-satify/pull/47))
  - added xor in operator list ([7eb3f0c](https://github.com/Mala1180/PPS-22-satify/commit/7eb3f0c903c3978e87685e83da056cd74fb53f2a)) ([#42](https://github.com/Mala1180/PPS-22-satify/pull/42))
  - removed changelog test ([bf28f21](https://github.com/Mala1180/PPS-22-satify/commit/bf28f2117872ac87e7c791c1bec132641a8af503)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - modify jar name ([fbddc0d](https://github.com/Mala1180/PPS-22-satify/commit/fbddc0d87161d8255b36dc68ced393e2d2c3b161)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - modify names ([f161028](https://github.com/Mala1180/PPS-22-satify/commit/f16102884124c405aa840252732ed564b66087fb)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - deleted changelog ([094c2bf](https://github.com/Mala1180/PPS-22-satify/commit/094c2bf304408f6c9e1b39eb833774ae4bba0ffa)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))
  - PartialModel as lexicographically-ordered Seq ([3e7f5b7](https://github.com/Mala1180/PPS-22-satify/commit/3e7f5b7620c62a8fecc99cac6ddec47aa43e7e1b)) ([#39](https://github.com/Mala1180/PPS-22-satify/pull/39))
  - chore (green): pure literal elimination ([cd3d059](https://github.com/Mala1180/PPS-22-satify/commit/cd3d0590881ca42749b84088498278f7120efa9d)) ([#39](https://github.com/Mala1180/PPS-22-satify/pull/39))
  - split dpll into multiple methods ([b830f98](https://github.com/Mala1180/PPS-22-satify/commit/b830f98debc52c632cbaf3e0f81b483ec3419a7e)) ([#38](https://github.com/Mala1180/PPS-22-satify/pull/38))
  - chore (green): unit propagation ([b3fbd27](https://github.com/Mala1180/PPS-22-satify/commit/b3fbd271c624689a5800ccf67267bc03d2642551)) ([#38](https://github.com/Mala1180/PPS-22-satify/pull/38))
  - random branching ([252058a](https://github.com/Mala1180/PPS-22-satify/commit/252058a441ed22549dac3596bf3b78d0d4734f34)) ([#38](https://github.com/Mala1180/PPS-22-satify/pull/38))

- release:
  - v0.1.1-test [skip ci] ([e8b576e](https://github.com/Mala1180/PPS-22-satify/commit/e8b576e9105f9e0be4b2839f06a3ba4d7554b4d9)) ([#41](https://github.com/Mala1180/PPS-22-satify/pull/41))

### Other Changes

- general:
  - prepare release, modify ci, backlog refinement ([8e357a8](https://github.com/Mala1180/PPS-22-satify/commit/8e357a8d91e51925b20af4cbdc140b504337f68f)) ([#57](https://github.com/Mala1180/PPS-22-satify/pull/57))

## [v0.1.0](https://github.com/Mala1180/PPS-22-satify/releases/tag/v0.1.0) - 2023-08-14 17:25:39

Prototype released at the end of the second Sprint.
It provides a GUI and the possibility to convert an expression in Conjunctive Normal Form.

### Feature 🚀

- general:
  - finish second backlog, print cnf output ([61bc583](https://github.com/Mala1180/PPS-22-satify/commit/61bc5833512c34d8b7ae9307ad1750bfbb54d1d9)) ([#34](https://github.com/Mala1180/PPS-22-satify/pull/34))
  - add DSL with simple operators ([88b4d50](https://github.com/Mala1180/PPS-22-satify/commit/88b4d50034c011b5d9167c3269c8fac0b8190a95)) ([#26](https://github.com/Mala1180/PPS-22-satify/pull/26))
  - added Solver entity and cnf converters ([664b4d6](https://github.com/Mala1180/PPS-22-satify/commit/664b4d603a7d4d71b08606bf7e0825aa9f2a8985)) ([#15](https://github.com/Mala1180/PPS-22-satify/pull/15))
  - setup GUI, add logo ([8eb13d6](https://github.com/Mala1180/PPS-22-satify/commit/8eb13d66192b930361713d18f675cb4c3db1583e)) ([#13](https://github.com/Mala1180/PPS-22-satify/pull/13))
  - setup architecture mvu with cake pattern ([ae10b4d](https://github.com/Mala1180/PPS-22-satify/commit/ae10b4d2687bf2ccacedba3c1c7c5f14251434c2)) ([#9](https://github.com/Mala1180/PPS-22-satify/pull/9))
  - added scalatest and file to test it ([53efa07](https://github.com/Mala1180/PPS-22-satify/commit/53efa072e1716f40862f5e980154a578c0133e4f)) ([#4](https://github.com/Mala1180/PPS-22-satify/pull/4))
  - added scalafmt.conf ([47a5b9f](https://github.com/Mala1180/PPS-22-satify/commit/47a5b9fb00c7757cf5a6520fe326cc164dfefd38)) ([#3](https://github.com/Mala1180/PPS-22-satify/pull/3))

### Bug Fixes 🐛

- general:
  - solve conflicts ([1a20181](https://github.com/Mala1180/PPS-22-satify/commit/1a20181c1955244e4a4c91cdc8f2c9a5a5a8fb40)) ([#35](https://github.com/Mala1180/PPS-22-satify/pull/35))
  - simplifyClosestAnd, chore (green): exhaustive search w/CNF simplification ([430a01e](https://github.com/Mala1180/PPS-22-satify/commit/430a01e9f3f96eba89db7ef509979304c8a3c33e)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - fix,refactor: conflictId -> isUnsat, chore (green): UNSAT cnf ([e3e7113](https://github.com/Mala1180/PPS-22-satify/commit/e3e711369a38b00ea8828bee09be992460f13100)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - again scalafmt ([3fd1173](https://github.com/Mala1180/PPS-22-satify/commit/3fd1173279691211cc1488ec2f02a74873b43491)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - removed ._2 notation, chenged doc for archunit and some gramatical errors, first print of CNF formula obtained ([9b0c45a](https://github.com/Mala1180/PPS-22-satify/commit/9b0c45aad56d8e9afe44b147a4ba7627d48d278c)) ([#31](https://github.com/Mala1180/PPS-22-satify/pull/31))
  - reformat code ([742826a](https://github.com/Mala1180/PPS-22-satify/commit/742826a4611352311be2d749b576e78bd5fea0d2)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - DPLL test ([53831b1](https://github.com/Mala1180/PPS-22-satify/commit/53831b15fb769ba8d39a625f2dac9d75a5945d67)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - fix tseitin method for single variable, little refactor and add tests, furthermore write some doc about testing: ArchUnit, ScalaTest and Cucumber ([018fefa](https://github.com/Mala1180/PPS-22-satify/commit/018fefa82315488de2e434addbe021c34d6aeb8b)) ([#29](https://github.com/Mala1180/PPS-22-satify/pull/29))
  - simplifyUpperMostOr propagation through the Or root ([4ca2990](https://github.com/Mala1180/PPS-22-satify/commit/4ca299037752ad8ad196352bd84d7ca95ee93e4c)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - transformation method and transformations tests ([d8e83ac](https://github.com/Mala1180/PPS-22-satify/commit/d8e83ac96e8476b0feb7f84839f68f2a4292e24e)) ([#29](https://github.com/Mala1180/PPS-22-satify/pull/29))
  - DPLL tests ([12884ab](https://github.com/Mala1180/PPS-22-satify/commit/12884ab36bcb1309c1cabfa00a23af2eafde0b40)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - fix conflicts ([5d46b92](https://github.com/Mala1180/PPS-22-satify/commit/5d46b92ed3177f2759763b7200ca7c19b0350d01)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - tests, undo CNF modifications, chore: add scaladoc in dpllcnfutils ([89df6f4](https://github.com/Mala1180/PPS-22-satify/commit/89df6f4b0a8c0c655965f43af547e3566925f181)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - ReplaceTest ([668b5cd](https://github.com/Mala1180/PPS-22-satify/commit/668b5cdc1acdb0f581a09fb03ef38cb4ae66a8ba)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - typo in dt's search test ([44f1711](https://github.com/Mala1180/PPS-22-satify/commit/44f1711e910ace303ab1858e0703db40c379a9b9)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - removed ambiguous symbol T from nested functions in Expression and TseitinTransformation method utils ([f97a5e2](https://github.com/Mala1180/PPS-22-satify/commit/f97a5e209ab0607002bf035b004b0ebc5d70d528)) ([#17](https://github.com/Mala1180/PPS-22-satify/pull/17))
  - initial implementation of Tseiting algorithm in particular substitution of variables: replace method ([bc93d18](https://github.com/Mala1180/PPS-22-satify/commit/bc93d189bec938fea862dfdf8dd785b530dcefa6)) ([#12](https://github.com/Mala1180/PPS-22-satify/pull/12))
  - solve merge conflict ([a1521ee](https://github.com/Mala1180/PPS-22-satify/commit/a1521eeb3339db55e536a191e10a238d2499694c)) ([#10](https://github.com/Mala1180/PPS-22-satify/pull/10))

### Refactor 🛠️

- general:
  - if then syntax ([d14d575](https://github.com/Mala1180/PPS-22-satify/commit/d14d575e643e9c46dffe368f23c700ffe9e40e07)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - little changes ([a30355c](https://github.com/Mala1180/PPS-22-satify/commit/a30355c23ff78ce2041991e9eed81c7863c11275)) ([#46](https://github.com/Mala1180/PPS-22-satify/pull/46))
  - test package name ([8abc344](https://github.com/Mala1180/PPS-22-satify/commit/8abc3441163fd06ecfb706e66eb6d925a389cb5c)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - dt data structure ([4ca81a4](https://github.com/Mala1180/PPS-22-satify/commit/4ca81a409eaa112f72ea29ea9cd9d79b6e2f6690)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - minimize repetitions ([bb813b6](https://github.com/Mala1180/PPS-22-satify/commit/bb813b6c805129fd260337be209a3b4bda2dd0bf)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - move PartialModel functions in separate object, test: add PartialModelUtilsTest ([3944b92](https://github.com/Mala1180/PPS-22-satify/commit/3944b926efef9fa4486654a513ef49817fe36ab2)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - move extractModelFromCnf, improve And simpl ([54f6db8](https://github.com/Mala1180/PPS-22-satify/commit/54f6db803ef9c2146212610c4281ba16625828de)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - refactor imports ([a85b2b3](https://github.com/Mala1180/PPS-22-satify/commit/a85b2b3310a8c61649874aaa5af21409dfb175be)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - move dpll object and utils ([dbfc6e4](https://github.com/Mala1180/PPS-22-satify/commit/dbfc6e41d0083745453131fb3e95e3bbfb150e06)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - remove Backtracking tree ([3ab189b](https://github.com/Mala1180/PPS-22-satify/commit/3ab189b3188c91444b214eaf675bef3658fec2ce)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - add package satify to tests ([58bad34](https://github.com/Mala1180/PPS-22-satify/commit/58bad3492e895e9d30e8b2a0eecf48c3cddd0b24)) ([#26](https://github.com/Mala1180/PPS-22-satify/pull/26))
  - refactor update function and add reflection ([fe2dd48](https://github.com/Mala1180/PPS-22-satify/commit/fe2dd488fc1d26a9ce7feec42250e71a6b7e9d0c)) ([#26](https://github.com/Mala1180/PPS-22-satify/pull/26))
  - rename VarConstraint -> Constraint ([9a2f72b](https://github.com/Mala1180/PPS-22-satify/commit/9a2f72b5e74bde1cb46f1bb40c9473db387ea62e)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - rename dpll to decision tree search, simple test of the DecisionTree, doc: scaladoc ([b468fbe](https://github.com/Mala1180/PPS-22-satify/commit/b468fbe9c69e1823041d4ba4f580ce7cc0fe5a3f)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - rename Assignment to VarConstraint ([0790747](https://github.com/Mala1180/PPS-22-satify/commit/0790747c091a415079cd1090ac6c034ee886b5d4)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - move utils code to ModelUtils, test: conversion, partial model extraction, expression map ([e2a48ea](https://github.com/Mala1180/PPS-22-satify/commit/e2a48eac612b7e47ad465c6e35e7bed654485543)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - move dtree code into separate packages ([6596dd8](https://github.com/Mala1180/PPS-22-satify/commit/6596dd8ca31f49db0801ce56b2c7f973cb452b2d)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - PartialModel to PartialExpression, test(tdd): basic decision tree structure ([0f2601c](https://github.com/Mala1180/PPS-22-satify/commit/0f2601c4ccd6e12b471f0082030bb435e8b31bb7)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - refactor GUI methods, add some doc ([d872d06](https://github.com/Mala1180/PPS-22-satify/commit/d872d06f09147270d410bf21ea99044365dd4637)) ([#15](https://github.com/Mala1180/PPS-22-satify/pull/15))
  - changed some names and removed some 'new' ([be08863](https://github.com/Mala1180/PPS-22-satify/commit/be08863247b9122d36d2e33c949c846ec73f3095)) ([#13](https://github.com/Mala1180/PPS-22-satify/pull/13))
  - add some doc, make more functional GUI ([23aea31](https://github.com/Mala1180/PPS-22-satify/commit/23aea31c2f35feab49120ed09daacf5665e5bbf9)) ([#11](https://github.com/Mala1180/PPS-22-satify/pull/11))

### Tests 🧪

- general:
  - other DPLL test ([1ddfa71](https://github.com/Mala1180/PPS-22-satify/commit/1ddfa7137a8b3211566039907e63dd23fa7d85cb)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - add simple SAT/USAT tests ([bb31540](https://github.com/Mala1180/PPS-22-satify/commit/bb3154003d9fc51b9f558af16a7891061f4b6a53)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - finished arch tests ([1fd5376](https://github.com/Mala1180/PPS-22-satify/commit/1fd53760d2b4e89634364beaa963d6673a3dafd9)) ([#20](https://github.com/Mala1180/PPS-22-satify/pull/20))
  - modify to test, add arch unit tests ([4503fae](https://github.com/Mala1180/PPS-22-satify/commit/4503fae2f2fac41c7eaa775ca262a81923444e76)) ([#20](https://github.com/Mala1180/PPS-22-satify/pull/20))
  - test protection rule ([7ffb15b](https://github.com/Mala1180/PPS-22-satify/commit/7ffb15bd10561ed363fa76f56f9a7d899712485e)) ([#35](https://github.com/Mala1180/PPS-22-satify/pull/35))

### Work in progress ⚙️

- general:
  - documentation about the implementation ([faeadb8](https://github.com/Mala1180/PPS-22-satify/commit/faeadb8853fdc26c066a98fd0485349a6c731946)) ([#31](https://github.com/Mala1180/PPS-22-satify/pull/31))
  - complete CNF transformation with tseitin method, to do some stress test and little refactor ([e409191](https://github.com/Mala1180/PPS-22-satify/commit/e4091912d1b983a8b47f6dccadb3756b357dd672)) ([#29](https://github.com/Mala1180/PPS-22-satify/pull/29))
  - test of transformation: i want the CNF form directly from the transform method without any mapping ([1e072c7](https://github.com/Mala1180/PPS-22-satify/commit/1e072c76de3b7ebb3e7494fc0aca166a456be6b0)) ([#29](https://github.com/Mala1180/PPS-22-satify/pull/29))
  - green tests for toCNF method using reduceRight ([ec611cc](https://github.com/Mala1180/PPS-22-satify/commit/ec611cc5c9b315aa52225736de9d13c093c4ff84)) ([#23](https://github.com/Mala1180/PPS-22-satify/pull/23))
  - green tests for implementation of transform method from expression to subexprs in CNF correctly ([a683b9c](https://github.com/Mala1180/PPS-22-satify/commit/a683b9ce032fd782f10f5912ee79b89ffc52a774)) ([#17](https://github.com/Mala1180/PPS-22-satify/pull/17))
  - green test for variable substitution ([27e4e9d](https://github.com/Mala1180/PPS-22-satify/commit/27e4e9d19632940e3f8ff93ebc2d7bdcdd133737)) ([#17](https://github.com/Mala1180/PPS-22-satify/pull/17))
  - refactor SubexpressionTest ([c7ed293](https://github.com/Mala1180/PPS-22-satify/commit/c7ed2932e4fed87f211c6d3dea522ef5d26efd74)) ([#12](https://github.com/Mala1180/PPS-22-satify/pull/12))
  - removed junit test dependency ([bd1c7e2](https://github.com/Mala1180/PPS-22-satify/commit/bd1c7e2996f9c65c6b63049a0afcf88d39c391cf)) ([#12](https://github.com/Mala1180/PPS-22-satify/pull/12))
  - initial implementation of Tseiting algorithm in particular substitution of variables: general refactor of methods, tests and documentation ([6c750ca](https://github.com/Mala1180/PPS-22-satify/commit/6c750caae6f07baa4cf06ee2597f33c21ae393d0)) ([#12](https://github.com/Mala1180/PPS-22-satify/pull/12))
  - initial implementation of Tseiting algorithm in particular substitution of variables: replace method refactoring ([e240458](https://github.com/Mala1180/PPS-22-satify/commit/e24045848b8c87a1355d73b110b24c70b7974119)) ([#12](https://github.com/Mala1180/PPS-22-satify/pull/12))
  - initial implementation of Tseiting algorithm in particular substitution of variables: replace method ([96e4fcf](https://github.com/Mala1180/PPS-22-satify/commit/96e4fcf6af1545146acf67a234b51bf65de0f822)) ([#12](https://github.com/Mala1180/PPS-22-satify/pull/12))
  - add model State, add some scaladoc ([655e2b0](https://github.com/Mala1180/PPS-22-satify/commit/655e2b0cc93401c88601bdd3f3b7ca69a9880d65)) ([#11](https://github.com/Mala1180/PPS-22-satify/pull/11))
  - initial implementation of Tseiting algorithm in particular decomposition of input expression ([3024cca](https://github.com/Mala1180/PPS-22-satify/commit/3024cca8f51f56d70eca7b4ea878e6abc8171ec8)) ([#12](https://github.com/Mala1180/PPS-22-satify/pull/12))

### Continuous Integration 🤖️

- general:
  - add scalafmt check to ci ([adcaeb4](https://github.com/Mala1180/PPS-22-satify/commit/adcaeb4a14eb69eb74041313495563b02ceac965)) ([#21](https://github.com/Mala1180/PPS-22-satify/pull/21))
  - added ci file ([c1e8664](https://github.com/Mala1180/PPS-22-satify/commit/c1e86646927bf67495c07e95ad09e3706c648b6e)) ([#5](https://github.com/Mala1180/PPS-22-satify/pull/5))

### Chores 🖋️

- general:
  - modify readme ([e5758ec](https://github.com/Mala1180/PPS-22-satify/commit/e5758ec22dd7e8b4f874a6b181879255afcb7035)) ([#35](https://github.com/Mala1180/PPS-22-satify/pull/35))
  - typo ([14b3366](https://github.com/Mala1180/PPS-22-satify/commit/14b33665011e2e782b13214d6f14ac0dd5c98b19)) ([#34](https://github.com/Mala1180/PPS-22-satify/pull/34))
  - DIMACS parser's parse function ([b1ad65e](https://github.com/Mala1180/PPS-22-satify/commit/b1ad65ea45e22aa20eb32b2a7c7f2be70d370ea2)) ([#46](https://github.com/Mala1180/PPS-22-satify/pull/46))
  - get and explode solutions ([a422411](https://github.com/Mala1180/PPS-22-satify/commit/a422411fd128bed43e22104418888ce13a639daf)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - explodeSolutions as cartesian product of PartialModels ([b10e0df](https://github.com/Mala1180/PPS-22-satify/commit/b10e0df220dd58c0a7656704006eac04b2c1bb37)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - chore (green): CNF without conflicts ([a4256f4](https://github.com/Mala1180/PPS-22-satify/commit/a4256f465dc8e669f65dd16581fd39ee68c570a0)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - improve dpll readability ([8f0bd1b](https://github.com/Mala1180/PPS-22-satify/commit/8f0bd1bc5001751db763f36896888fa5053690e1)) ([#33](https://github.com/Mala1180/PPS-22-satify/pull/33))
  - add simple Or/Literals test ([513d133](https://github.com/Mala1180/PPS-22-satify/commit/513d133da982f091de5a22b158376d1fa67ef513)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - minimize asInstanceOf repetitions ([5c6b761](https://github.com/Mala1180/PPS-22-satify/commit/5c6b761961681948a10eb059dc9be440156b5a2e)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - cnf simpl pos and neg literal ([4e820a5](https://github.com/Mala1180/PPS-22-satify/commit/4e820a5021afcff5f124f81e14c9f18569688e34)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - improve simplify closest Or with complex CNF, refactor: rename classes and updateCnf ([fc708ca](https://github.com/Mala1180/PPS-22-satify/commit/fc708ca6db70d019d45be6c169554329be16d921)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - CNF semplification on necessary branches ([408ef95](https://github.com/Mala1180/PPS-22-satify/commit/408ef955ca8c12a13082caa44b5aebeedcdb681c)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - add constants, refactor code ([f213574](https://github.com/Mala1180/PPS-22-satify/commit/f2135746faa1a177a64a5b8a26fd2c69d23cdcf8)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - add updateCnf into dpll, restore DecisionTreeSearchTest and refactor in DPLLTest ([fa82a15](https://github.com/Mala1180/PPS-22-satify/commit/fa82a151e9093cbfb25c652d17b2c379cfcab87c)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - improve updateCnf using generics ([51fb420](https://github.com/Mala1180/PPS-22-satify/commit/51fb420d38645b6fcab98785bd304ca2cfa8ca25)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - rewrite DpllExpressionUtils using Cnf, remove useless functions, remove Backtracking tree test ([a8f460f](https://github.com/Mala1180/PPS-22-satify/commit/a8f460fe3d8dd4d1416d98ceb55adb8805d6a634)) ([#30](https://github.com/Mala1180/PPS-22-satify/pull/30))
  - fixed one test ([394b243](https://github.com/Mala1180/PPS-22-satify/commit/394b243bacddfab4c1539f27dc1550af50b4df96)) ([#26](https://github.com/Mala1180/PPS-22-satify/pull/26))
  - remove a space ([54ea80a](https://github.com/Mala1180/PPS-22-satify/commit/54ea80ab704ba351cdb20d3ade05b363d1415940)) ([#22](https://github.com/Mala1180/PPS-22-satify/pull/22))
  - format all files again ([34244d8](https://github.com/Mala1180/PPS-22-satify/commit/34244d8ec9f3cef7c5144da29336203a1c9a8823)) ([#21](https://github.com/Mala1180/PPS-22-satify/pull/21))
  - format files ([62400f6](https://github.com/Mala1180/PPS-22-satify/commit/62400f6babccda9a2855672e3c7f18ecf9cd2e5b)) ([#21](https://github.com/Mala1180/PPS-22-satify/pull/21))
  - adjust some doc and name ([cba210e](https://github.com/Mala1180/PPS-22-satify/commit/cba210ef3929e843eb37887178018c110cb0951c)) ([#20](https://github.com/Mala1180/PPS-22-satify/pull/20))
  - dpll as a simple search tree ([e922c20](https://github.com/Mala1180/PPS-22-satify/commit/e922c20a0cc8569e9483a65243838e660d51cdea)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - generic mapExp, default parExp -> assignExp to false ([c078476](https://github.com/Mala1180/PPS-22-satify/commit/c07847613f7e54e7b81c99c288b3e48f50bc72c0)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - del cast, add generic mapExp to DpllExpressionUtils ([f30c08d](https://github.com/Mala1180/PPS-22-satify/commit/f30c08d210308ac21787608a675cf3f018b0fca2)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - rename convert fun to cast, ModelUtils -> DPLLUtils, test: DecisionTree's decision ([18af9ef](https://github.com/Mala1180/PPS-22-satify/commit/18af9ef34e48dc58b48482485b0585b5e06f2184)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - delete code repetitions on variable assignment ([f6e9370](https://github.com/Mala1180/PPS-22-satify/commit/f6e9370ffdfbdf11992b3f70412173af09741e36)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - variable assignment in expression ([8bc93e1](https://github.com/Mala1180/PPS-22-satify/commit/8bc93e10977fddb551d75f13195a0401e5d07fb4)) ([#16](https://github.com/Mala1180/PPS-22-satify/pull/16))
  - remove Operator, model Expression with generics, fix: conflicts from merge ([c1a458a](https://github.com/Mala1180/PPS-22-satify/commit/c1a458a05c9bf462a1c3e1db5e8a48644d41ec7b)) ([#14](https://github.com/Mala1180/PPS-22-satify/pull/14))
  - add Variable, create EmptyModel and PartialModel concepts ([4dcc1d7](https://github.com/Mala1180/PPS-22-satify/commit/4dcc1d799bc00caafc7fc5ac0abc81e1e7c19249)) ([#14](https://github.com/Mala1180/PPS-22-satify/pull/14))
  - BacktrackingTree, test: initial tree ([4ec25b2](https://github.com/Mala1180/PPS-22-satify/commit/4ec25b29c7a117727e63b514acb61b0e749cbcf7)) ([#14](https://github.com/Mala1180/PPS-22-satify/pull/14))
  - remove wrap in scaladoc scalafmt ([b8dc9b1](https://github.com/Mala1180/PPS-22-satify/commit/b8dc9b197371440db5d19cf3eb55f15d721b6611)) ([#11](https://github.com/Mala1180/PPS-22-satify/pull/11))
  - ignore .idea ([e95ce72](https://github.com/Mala1180/PPS-22-satify/commit/e95ce72421a839da0b242b9aed1dde1ecd6cb4ac)) ([#10](https://github.com/Mala1180/PPS-22-satify/pull/10))
  - modified project idea files ([1660417](https://github.com/Mala1180/PPS-22-satify/commit/16604179c896611e0558e124ffc1bd8b1c48799d)) ([#2](https://github.com/Mala1180/PPS-22-satify/pull/2))

### Other Changes

- general:
  - solve conflicts ([3123150](https://github.com/Mala1180/PPS-22-satify/commit/312315076afc0399b575a6d6da8b78e9c01cb01b)) ([#26](https://github.com/Mala1180/PPS-22-satify/pull/26))
  - solve conflicts ([f675e24](https://github.com/Mala1180/PPS-22-satify/commit/f675e240e36eb336c39e65729e2b37f3e554839a)) ([#21](https://github.com/Mala1180/PPS-22-satify/pull/21))
  - solve conflict ([ddaea51](https://github.com/Mala1180/PPS-22-satify/commit/ddaea51c73efc2d0948cfc3b92216671f51f422f)) ([#20](https://github.com/Mala1180/PPS-22-satify/pull/20))
  - solved conflict ([25cec63](https://github.com/Mala1180/PPS-22-satify/commit/25cec63c82f4dcc5715a0f048d65897e54dadf9d)) ([#15](https://github.com/Mala1180/PPS-22-satify/pull/15))
  - changes to .idea files ([5fd98e4](https://github.com/Mala1180/PPS-22-satify/commit/5fd98e40f4f1fec1ed96cdf6941a328676b60f8f)) ([#10](https://github.com/Mala1180/PPS-22-satify/pull/10))
  - add some doc on MVU ([4b10680](https://github.com/Mala1180/PPS-22-satify/commit/4b10680b8b850b1031e20615936c201a8aafd942)) ([#8](https://github.com/Mala1180/PPS-22-satify/pull/8))
  - updated .idea ([cd5e485](https://github.com/Mala1180/PPS-22-satify/commit/cd5e485f789c16497ce240aa94a3b7915f5a06e1)) ([#3](https://github.com/Mala1180/PPS-22-satify/pull/3))
  - introduced readme and report ([6a1bf21](https://github.com/Mala1180/PPS-22-satify/commit/6a1bf21422b076a8ba02270a3f661fa0dfd050ac)) ([#4](https://github.com/Mala1180/PPS-22-satify/pull/4))

\* *This CHANGELOG was automatically generated by [auto-generate-changelog](https://github.com/BobAnkh/auto-generate-changelog)*
