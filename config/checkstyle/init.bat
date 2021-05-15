@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  checkstyle pre commit 설정파일
@rem
@rem ##########################################################################


copy "pre-commit" "..\..\.git\hooks\pre-commit"
echo Checkstyle pre commit setup completed