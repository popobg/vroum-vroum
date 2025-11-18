@echo off
REM Script : Compile rapport.tex en PDF avec MiKTeX (pdflatex + biber)
REM Usage : double-cliquer ou lancer depuis l'invite de commandes dans le dossier contenant rapport.tex

setlocal enabledelayedexpansion

set "TEXFILE=rapport.tex"
set "BASENAME=rapport"

echo 1/4 : Premier passage pdflatex...
pdflatex -interaction=nonstopmode -halt-on-error "%TEXFILE%"
if errorlevel 1 (
  echo Erreur : pdflatex a échoué au premier passage. Consulter %BASENAME%.log
  exit /b 1
)

echo 2/4 : Lancement de biber (si vous utilisez biblatex)...
biber "%BASENAME%"
if errorlevel 1 (
  echo Erreur : biber a échoué. Si vous utilisez bibtex, remplacez cette étape par 'bibtex %BASENAME%'.
  exit /b 1
)

echo 3/4 : Second passage pdflatex...
pdflatex -interaction=nonstopmode -halt-on-error "%TEXFILE%"
if errorlevel 1 (
  echo Erreur : pdflatex a échoué au second passage. Consulter %BASENAME%.log
  exit /b 1
)

echo 4/4 : Passage final pdflatex (mise à jour des références)...
pdflatex -interaction=nonstopmode -halt-on-error "%TEXFILE%"
if errorlevel 1 (
  echo Erreur : pdflatex a échoué au dernier passage. Consulter %BASENAME%.log
  exit /b 1
)

echo Compilation réussie : %BASENAME%.pdf
endlocal
exit /b 0