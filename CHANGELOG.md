# CHANGELOG

## [v0.3.0](https://github.com/Mala1180/PPS-22-satify/releases/tag/v0.3.0) - 2023-09-11 19:31:39

## [v0.2.0](https://github.com/Mala1180/PPS-22-satify/releases/tag/v0.2.0) - 2023-08-28 16:51:56

Prototype released at the end of the third Sprint.
It permits solving simple SAT problem instances and provides a richer DSL for the input. 

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
