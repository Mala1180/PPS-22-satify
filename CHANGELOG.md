# CHANGELOG

## [v0.3.0](https://github.com/Mala1180/PPS-22-satify/releases/tag/v0.3.0) - 2023-09-11 19:31:39

## What's Changed
* Align Develop with Main by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/58
* Feature/backlog-sprint-4 by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/59
* Feature/cucumber-plugin by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/60
* Feature/gui-tab-improvement by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/67
* Feature/automatic-changelog by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/66
* Feature/tstn-tests-improvement by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/68
* Feature/reflection-bdd-test by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/71
* Feature/fix-input by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/69
* Feature/input-error-handling by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/70
* Feature/documentation-update by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/72
* Feature/documentation-tseitin-impl by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/74
* Feature/gui-minor-fix by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/75
* Feature/requirements-refinement by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/73
* Feature/solver-refactor by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/76
* Feature/tseitin-optimization by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/78
* Feature/update-tests by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/77
* Feature/tseitin-transform-refactor by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/79
* Feature/update-organization by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/81
* Feature/converter-memoize by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/80
* Feature/solver-cache  by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/83
* Feature/dpll-tailrec by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/84
* Feature/tseitin-bdd-test by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/85
* Feature/tailrec-parser-cnf-refactor by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/86
* Feature/problem-representation by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/87
* Feature/dimacs-dump by @paga16-hash in https://github.com/Mala1180/PPS-22-satify/pull/88
* Feature/encoding-completion by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/90
* Feature/next-sol-mechanism by @w-disaster in https://github.com/Mala1180/PPS-22-satify/pull/91
* Release/0.3.0 by @Mala1180 in https://github.com/Mala1180/PPS-22-satify/pull/92


**Full Changelog**: https://github.com/Mala1180/PPS-22-satify/compare/v0.2.0...v0.3.0

### Feature 🚀

- general:
  - sequential encoding for at most k constraint ([2e218db](https://github.com/Mala1180/PPS-22-satify/commit/2e218db3ce20d496d83e149828d01d36aeebf254)) ([#90](https://github.com/Mala1180/PPS-22-satify/pull/90))
  - finish GraphColoring, missing atMostK for NurseScheduling ([1beb019](https://github.com/Mala1180/PPS-22-satify/commit/1beb019e439cc58824c0a572a606388d20cc10df)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - add reflection bdd test and fixed name variables ([4de7d7f](https://github.com/Mala1180/PPS-22-satify/commit/4de7d7fff5db3d4d0c64697e22f05b974ae96c8f)) ([#71](https://github.com/Mala1180/PPS-22-satify/pull/71))
  - remove cucumber plug, run manually cucumber ([2a6b068](https://github.com/Mala1180/PPS-22-satify/commit/2a6b068a46b754a55d629fc16bae2318547dd571)) ([#60](https://github.com/Mala1180/PPS-22-satify/pull/60))

### Bug Fixes 🐛

- general:
  - NextSolutionUpdateTest ([c581987](https://github.com/Mala1180/PPS-22-satify/commit/c581987dbb4c2d93d1d4a5ba0da06e6ba5117979)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - fix some test ([a0c0ca0](https://github.com/Mala1180/PPS-22-satify/commit/a0c0ca034936b6c7fd9000c08e30e761b01de259)) ([#90](https://github.com/Mala1180/PPS-22-satify/pull/90))
  - DpllOneSolTest ([e884c08](https://github.com/Mala1180/PPS-22-satify/commit/e884c084a185f6b9da3e4d5d5683be0771c6bb51)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - fix loop print nqueens ([7b1971c](https://github.com/Mala1180/PPS-22-satify/commit/7b1971cd266638ddaca2d31b463a7a9caebc28e3)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - solve conflict ([eee83f6](https://github.com/Mala1180/PPS-22-satify/commit/eee83f6ad2fe189a3512a7d7246af63d3ad887d9)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - merge conflicts ([44321e1](https://github.com/Mala1180/PPS-22-satify/commit/44321e14b6fb3b7d0592db39e8b9037ed1a8cd4e)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - merge conflicts ([19ade19](https://github.com/Mala1180/PPS-22-satify/commit/19ade19fae373c292be913227a2f2607b2038324)) ([#86](https://github.com/Mala1180/PPS-22-satify/pull/86))
  - extract solutions var filter ([cfc69d3](https://github.com/Mala1180/PPS-22-satify/commit/cfc69d35de0890c2b209baec0092cb234046c1ae)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - delete Variable import in tests, format code ([83553c4](https://github.com/Mala1180/PPS-22-satify/commit/83553c43eca18f631b48e0957391a1bd1305d7f8)) ([#86](https://github.com/Mala1180/PPS-22-satify/pull/86))
  - fix ciclic import ([a910d73](https://github.com/Mala1180/PPS-22-satify/commit/a910d73da4bb4a638b7a0003f16d8e1b4a47ac5d)) ([#85](https://github.com/Mala1180/PPS-22-satify/pull/85))
  - converter trait scaladoc ([cda7ad5](https://github.com/Mala1180/PPS-22-satify/commit/cda7ad59c616ffc527a2713b4061de8dc658d2cc)) ([#80](https://github.com/Mala1180/PPS-22-satify/pull/80))
  - remove state impl with show parameter ([fd1a023](https://github.com/Mala1180/PPS-22-satify/commit/fd1a023a2975bcaa0f882158e54825f445054580)) ([#81](https://github.com/Mala1180/PPS-22-satify/pull/81))
  - pure literal elimination function ([307b5cc](https://github.com/Mala1180/PPS-22-satify/commit/307b5cc27ab2dea3592a59ca46dc507cbacefa47)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - solve conflicts ([09c3f6b](https://github.com/Mala1180/PPS-22-satify/commit/09c3f6be050f8c7a9efd70487bb29ed0d58f9e07)) ([#76](https://github.com/Mala1180/PPS-22-satify/pull/76))
  - solve conflicts ([b52e164](https://github.com/Mala1180/PPS-22-satify/commit/b52e164c4f4a911116b0c33428fb28ab8ef51a2e)) ([#76](https://github.com/Mala1180/PPS-22-satify/pull/76))
  - wrong package model.update to update ([8c567b1](https://github.com/Mala1180/PPS-22-satify/commit/8c567b18dd78102f4f00fad741dd010b833952e4)) ([#77](https://github.com/Mala1180/PPS-22-satify/pull/77))
  - optional input management in View.scala ([a539043](https://github.com/Mala1180/PPS-22-satify/commit/a539043654d518898f1f9551fa60da97d880a5da)) ([#75](https://github.com/Mala1180/PPS-22-satify/pull/75))
  - solve comnflict ([4eb646f](https://github.com/Mala1180/PPS-22-satify/commit/4eb646f08a114407c19db254243c13d2e92a2f07)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - fix access token ([c8dea0e](https://github.com/Mala1180/PPS-22-satify/commit/c8dea0e19e673c87426ef8e6973487c5de8b8aea)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - type in TestArchitecture ([2d2b901](https://github.com/Mala1180/PPS-22-satify/commit/2d2b90104acae4f2c4d161107048dae813d3e48c)) ([#60](https://github.com/Mala1180/PPS-22-satify/pull/60))

### Documentation 📚

- general:
  - updated implementation part ([c858f5a](https://github.com/Mala1180/PPS-22-satify/commit/c858f5a6a7cae904f3da1db31390d8384518dd8e)) ([#76](https://github.com/Mala1180/PPS-22-satify/pull/76))
  - pair programming - improve requirements, change color to buttons, change CNFConverter to Converter ([af81658](https://github.com/Mala1180/PPS-22-satify/commit/af8165881794649411c92d98e41894cbfd4c3933)) ([#73](https://github.com/Mala1180/PPS-22-satify/pull/73))
  - updated introduction and methodology ([eee7832](https://github.com/Mala1180/PPS-22-satify/commit/eee78324d5f074ace3d0581a03935a67bb2ad342)) ([#72](https://github.com/Mala1180/PPS-22-satify/pull/72))

### Refactor 🛠️

- general:
  - rename optimization-based decisions, optimization methods ([f1de0b8](https://github.com/Mala1180/PPS-22-satify/commit/f1de0b8487713488c4d1e3f804ee35087e1f1bf4)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - refator message SolveProblem and trait Problem ([8b7d817](https://github.com/Mala1180/PPS-22-satify/commit/8b7d817a2b379cdbc45fa40eeed9183b6c1a3f05)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - dpll package structure ([a8471d1](https://github.com/Mala1180/PPS-22-satify/commit/a8471d1ea7d3c866ea455cb9c4387d77ef195492)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - rename LitSearch enum ([ed2af7c](https://github.com/Mala1180/PPS-22-satify/commit/ed2af7c41ab19ea8f2d2182c0c6290d0e461fa4a)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - transformation method and tests ([a16360a](https://github.com/Mala1180/PPS-22-satify/commit/a16360aea900343cbb862d3c6e00f92f0da06000)) ([#79](https://github.com/Mala1180/PPS-22-satify/pull/79))
  - small name refactoring ([03a9cbc](https://github.com/Mala1180/PPS-22-satify/commit/03a9cbc861e69ff3d6cd66cf56bc91dc6c76136d)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - comment prev DPLL version ([3ba1f0f](https://github.com/Mala1180/PPS-22-satify/commit/3ba1f0fc177bbd5fa6566cc039d5e361d25f8b20)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - refactor test packages ([49037de](https://github.com/Mala1180/PPS-22-satify/commit/49037de51473411c54c9401fa645585b93fe2bd7)) ([#76](https://github.com/Mala1180/PPS-22-satify/pull/76))
  - modify Solver entity and introduce Converter entity, making tseitin and dpll implemenetation private ([e13c5c2](https://github.com/Mala1180/PPS-22-satify/commit/e13c5c28317eb0eba7f224394e6f6b8921d0e9d3)) ([#76](https://github.com/Mala1180/PPS-22-satify/pull/76))
  - modify regex pattern and atMost tests ([2b22f92](https://github.com/Mala1180/PPS-22-satify/commit/2b22f92a4895f2c4ed04801b2eacffba6820edea)) ([#71](https://github.com/Mala1180/PPS-22-satify/pull/71))
  - add a string input to Model, modify print of expression in gui ([dd93695](https://github.com/Mala1180/PPS-22-satify/commit/dd936957be5060b6f983b0d41853fe78e6c867a1)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - view import update ([c0e940b](https://github.com/Mala1180/PPS-22-satify/commit/c0e940bae5079db1543fc403f2b8cdfff81d22b5)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - loading label ([70d7d77](https://github.com/Mala1180/PPS-22-satify/commit/70d7d7730836eeff8a94a26ef06993eb175158c2)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - view update ([c35dac2](https://github.com/Mala1180/PPS-22-satify/commit/c35dac2edfcc04125d67cab024c24c483a6a1074)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - reactions and view update, to check: print for import ([03b19a3](https://github.com/Mala1180/PPS-22-satify/commit/03b19a3ec13ac8b984a661f9f005a4510109c3e1)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))

### Style Improvements 💉

- general:
  - format ([d3b5610](https://github.com/Mala1180/PPS-22-satify/commit/d3b56100c556b8a9c0a86fc65f601bcf535f0b79)) ([#90](https://github.com/Mala1180/PPS-22-satify/pull/90))
  - format ([737a9ad](https://github.com/Mala1180/PPS-22-satify/commit/737a9ade3fb3496d180dec53323311a4183f8463)) ([#81](https://github.com/Mala1180/PPS-22-satify/pull/81))
  - format ([1b337f0](https://github.com/Mala1180/PPS-22-satify/commit/1b337f0e9677a45a52e37b03daf9b47751aca237)) ([#77](https://github.com/Mala1180/PPS-22-satify/pull/77))
  - format ([6d07482](https://github.com/Mala1180/PPS-22-satify/commit/6d07482516af7660213412bbd07cf31c5b18dc2e)) ([#68](https://github.com/Mala1180/PPS-22-satify/pull/68))

### Work in progress ⚙️

- general:
  - dump function ([814359f](https://github.com/Mala1180/PPS-22-satify/commit/814359fab4f9bc91388f2345f8a377d946c1f1e6)) ([#88](https://github.com/Mala1180/PPS-22-satify/pull/88))
  - add next in Solver, problem creating new instance of Solver ([f0d8e63](https://github.com/Mala1180/PPS-22-satify/commit/f0d8e634a9afc5a6cc57b631e2e7d9fdd095a572)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - generation of input area for parameters ([e558c9b](https://github.com/Mala1180/PPS-22-satify/commit/e558c9b0102ba8cb5d567e2715975254742dc13b)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - linking problems with gui ([a12c015](https://github.com/Mala1180/PPS-22-satify/commit/a12c0157aa50cb845487779469c685850d8fcfab)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - introduce NurseSceduling, modify parameters of GraphColoring ([bd1419a](https://github.com/Mala1180/PPS-22-satify/commit/bd1419af7b6686e3bbf123340a90edf2ece99b96)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - start GraphColoring ([ae8cf58](https://github.com/Mala1180/PPS-22-satify/commit/ae8cf583f19e3a2ffc805061c520c1bfb7cca64e)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - making Problem entity ([8407b6e](https://github.com/Mala1180/PPS-22-satify/commit/8407b6e359cb23dc272804d76f912a63da6f0551)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - test formal print ([7c05a26](https://github.com/Mala1180/PPS-22-satify/commit/7c05a26b9f1b98b82e5bbe617589031b9e058a49)) ([#68](https://github.com/Mala1180/PPS-22-satify/pull/68))
  - show next solution ([b7cbf7f](https://github.com/Mala1180/PPS-22-satify/commit/b7cbf7f9aff895ffaff0ef13a64c1cc4a1abb5d1)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - next button ([875111c](https://github.com/Mala1180/PPS-22-satify/commit/875111cb5f828ceea80db747ad2e419bc145f075)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - resize window frame and remove space between logo and input area ([cd0329c](https://github.com/Mala1180/PPS-22-satify/commit/cd0329c6b8cf0f5826ec92f2dd66083edf19216b)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - correct expression input text area and refactored components updating ([db95ffa](https://github.com/Mala1180/PPS-22-satify/commit/db95ffa070b493c56170f1d7cc712954c118911b)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - add next button properly aligned ([31a7057](https://github.com/Mala1180/PPS-22-satify/commit/31a7057bffd469e9683c1715c4c0e04e025816df)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - add operators to help dialog and align error dialog ([a140c66](https://github.com/Mala1180/PPS-22-satify/commit/a140c66f485bbacd9541a328a5cc55f6acc01838)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - gui improvement, fix on problem selection from gui ([c7aea6a](https://github.com/Mala1180/PPS-22-satify/commit/c7aea6a8408aa3f84700659af5d7b2057e29f39f)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - add error popup, add loading, show results ([5233178](https://github.com/Mala1180/PPS-22-satify/commit/52331784a16cad418c64079fe71436ced9f7ed7c)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - found possible solution for cucumber ([dfe41b0](https://github.com/Mala1180/PPS-22-satify/commit/dfe41b0afd75472264575f43471e58c3ea5a0932)) ([#60](https://github.com/Mala1180/PPS-22-satify/pull/60))
  - link problem combobox to implementation ([0f7292f](https://github.com/Mala1180/PPS-22-satify/commit/0f7292f025c000ca04c8223ca6cb07af139f5b80)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - reintroduce reflection test, add cucumber plugin, fixing test ([ae981bf](https://github.com/Mala1180/PPS-22-satify/commit/ae981bf0591a29387311b9fa9a32c3fe303e01a2)) ([#60](https://github.com/Mala1180/PPS-22-satify/pull/60))
  - add TabPane to show problems ([6cfe1f7](https://github.com/Mala1180/PPS-22-satify/commit/6cfe1f70401c8aebd6e9d372bd878b1fc85f37f7)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - introduce help dialog ([8dddfcb](https://github.com/Mala1180/PPS-22-satify/commit/8dddfcb21bf41c915af2704fefd7fc39677afd6e)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))

### Continuous Integration 🤖️

- general:
  - modify tag and release version ([9b5d01a](https://github.com/Mala1180/PPS-22-satify/commit/9b5d01a0f01ef7471f4f1c4815c9b5afa24756e2)) ([#92](https://github.com/Mala1180/PPS-22-satify/pull/92))
  - modify tag ([2c644ea](https://github.com/Mala1180/PPS-22-satify/commit/2c644ea24c2b291d26a866e3eb2955e3ce89e0e6)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - finished changelog ([8f1c404](https://github.com/Mala1180/PPS-22-satify/commit/8f1c40489338f013b8a04fa0c52e3f12198fea80)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - modify access token ([6791347](https://github.com/Mala1180/PPS-22-satify/commit/6791347eb5902a5addb625c5560f9e66d1d6f9d3)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - add generate changelog ([81f8ca4](https://github.com/Mala1180/PPS-22-satify/commit/81f8ca4eb4032d7e6659487b2f2b8c26b4a61477)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - modify releae title ([e5ad9eb](https://github.com/Mala1180/PPS-22-satify/commit/e5ad9eb0e8b93dfab4b134b351945acd565046f6)) ([#61](https://github.com/Mala1180/PPS-22-satify/pull/61))
  - add cucumber run in ci ([a99ee40](https://github.com/Mala1180/PPS-22-satify/commit/a99ee40bddbac9cf32fa5d0875b6c0e6e334f084)) ([#60](https://github.com/Mala1180/PPS-22-satify/pull/60))

### Chores 🖋️

- general:
  - integrate next solution into Solver ([3740802](https://github.com/Mala1180/PPS-22-satify/commit/37408021ba5bc6f99f00045ba28353bc6b647bb6)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - modify some name, exp is now a method ([c1acad6](https://github.com/Mala1180/PPS-22-satify/commit/c1acad65f43e5bbef79b385e54f5a282fa058b09)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - constraints are now defined in trait Problem ([fd09f42](https://github.com/Mala1180/PPS-22-satify/commit/fd09f421573bb97521a80c9c7616738fbcc998d4)) ([#87](https://github.com/Mala1180/PPS-22-satify/pull/87))
  - extractSolutions w/explodeSolutions ([0ce3270](https://github.com/Mala1180/PPS-22-satify/commit/0ce32705c359b3894c62b01b1fc9bde922c1caec)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - add extractSolution to get one solution at a time, need to manage explodeSolutions ([8eaff96](https://github.com/Mala1180/PPS-22-satify/commit/8eaff9655d00628fffc91dd714e8886272a4e6f1)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - integrate complete decision with optimizations in next sol dpll ([84a082c](https://github.com/Mala1180/PPS-22-satify/commit/84a082c35f1f77c27e806036228d3405fbd3c4e1)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - add dpll which takes cnf as input ([0940bec](https://github.com/Mala1180/PPS-22-satify/commit/0940bec6476130d6e0354742c69df0a964981f96)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - dpll next sol PoC ([10358ce](https://github.com/Mala1180/PPS-22-satify/commit/10358ced2824f6abf38d2daad41dc412c5493d9b)) ([#91](https://github.com/Mala1180/PPS-22-satify/pull/91))
  - delete Variable in CNF Symbol ([6a760c2](https://github.com/Mala1180/PPS-22-satify/commit/6a760c295be4cde1b9b2a1dff9f227b7576f5ef5)) ([#86](https://github.com/Mala1180/PPS-22-satify/pull/86))
  - tailrec function in parser ([eb7d2a1](https://github.com/Mala1180/PPS-22-satify/commit/eb7d2a1da8be14473fd06defbbf76cc813a8386d)) ([#86](https://github.com/Mala1180/PPS-22-satify/pull/86))
  - delete DPLLTest ([93c3c70](https://github.com/Mala1180/PPS-22-satify/commit/93c3c707dac9a6937d82bb58c3e025b071a47ed9)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - comment NQueens 10x10 test ([ec2f90b](https://github.com/Mala1180/PPS-22-satify/commit/ec2f90b389f45e5c97c60f326cd186939cb022ef)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - delete prev DPLL version ([1f2c5ee](https://github.com/Mala1180/PPS-22-satify/commit/1f2c5ee5ed526b67e9a1fb224311bbf354216864)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - dpll decision function to private, format code ([ddcfdd4](https://github.com/Mala1180/PPS-22-satify/commit/ddcfdd4522fc0a70d52fd46caf8d23449554f25c)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - add pure literal elimination in new dpll version ([aea9a2b](https://github.com/Mala1180/PPS-22-satify/commit/aea9a2b00c62c542d03751c83f1ca0718226485a)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - tailrec dpll with simple decision ([8af90b9](https://github.com/Mala1180/PPS-22-satify/commit/8af90b975c8613fa1e4b42bbfad2b8ec8f724bbb)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - enable multithreading in not tailrec dpll ([64c9d66](https://github.com/Mala1180/PPS-22-satify/commit/64c9d66f7fb5b7450816930b26773af25556384a)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - tailrec isUnsat ([25ca938](https://github.com/Mala1180/PPS-22-satify/commit/25ca9380e811b48f9bdb3615d8f74e0340f0d2c4)) ([#84](https://github.com/Mala1180/PPS-22-satify/pull/84))
  - modify help section ([4f947ad](https://github.com/Mala1180/PPS-22-satify/commit/4f947ad4d904616c2e00cdb4274530fe3a973860)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - remove useless factory methods ([2732565](https://github.com/Mala1180/PPS-22-satify/commit/273256535c9c47073800c71f9b59f2fb34066459)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - empty changelog ([901bc1e](https://github.com/Mala1180/PPS-22-satify/commit/901bc1edd780b6c2bbca5a2347bc19c0e842c9e5)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - trigger release on edit ([2fb9ab7](https://github.com/Mala1180/PPS-22-satify/commit/2fb9ab7975772eb3ca14d8cd889afbb48dfd096f)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - modify tag ([bfeb549](https://github.com/Mala1180/PPS-22-satify/commit/bfeb54983939396ca6faabe0792643a44a030e68)) ([#66](https://github.com/Mala1180/PPS-22-satify/pull/66))
  - remove useless print ([9fa178b](https://github.com/Mala1180/PPS-22-satify/commit/9fa178b741986081d7bfd6a3fe5671dca12451c5)) ([#67](https://github.com/Mala1180/PPS-22-satify/pull/67))
  - empty envVars for cucumber run ([937fdaa](https://github.com/Mala1180/PPS-22-satify/commit/937fdaab9528d309e2386c11ad6781479ea5003f)) ([#60](https://github.com/Mala1180/PPS-22-satify/pull/60))
  - remove import ([97d3d54](https://github.com/Mala1180/PPS-22-satify/commit/97d3d54d73b0a1ac32daf509c37eb153e72517d9)) ([#60](https://github.com/Mala1180/PPS-22-satify/pull/60))

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
