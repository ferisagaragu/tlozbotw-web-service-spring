apidoc -i src/ -o apidoc/
robocopy .\\style .\\apidoc\\css style.css /s
robocopy .\\style .\\apidoc\\vendor prettify.css /s
start /apidoc/index.html