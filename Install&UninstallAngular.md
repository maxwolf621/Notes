[Uninstall&Install Angular](https://github.com/angular/angular-cli/issues/1190)  
[Cache Clean](https://stackoverflow.com/questions/39566257/how-to-uninstall-upgrade-angular-cli)  
[node_module](https://stackoverflow.com/questions/63294260/what-is-the-purpose-of-node-modules-folder)  
[To install Node.js](https://hackmd.io/6Nvu-p8aQ0ynRhhsppBbww)  


## Install Angular

Reinstall angular  
```bash
npm uninstall -g @angular/cli
# delete cache
npm cache verify
npm install -g @angular/cli@latest
# Create node_module's fold
npm install
```

Angular will be installed in this directory
```bash 
/usr/lib/node_modules
```

Create a `package.json` for the local npm package
[ref1](https://www.sitepoint.com/npm-guide/)  
[ref2](https://stackoverflow.com/questions/9484829/npm-cant-find-package-json)  
```bash
npm init
```

npm ERR! enoent ENOENT: no such file or directory, open '/usr/lib/node_modules/package.json'
```bash
# Follwing the below steps you well get package.json file.
npm --version
npm install express
npm init -y
```

## install `node`

```bash
nvm install node # "node" is an alias for the latest version
```


## Install `nvm`
[](https://github.com/nvm-sh/nvm#install--update-script)

```bash
## Download the package
wget -qO- https://raw.githubusercontent.com/nvm-sh/nvm/v0.38.0/install.sh | bash

## Source it to ~/.profile or ~/.bashrc
Source ~/.bashrc

## Use node
nvm use node
```

## Install `npm` 

update latest npm 
```bash
nvm install-latest-npm
```


## `.nvmrc`

default path : `~/.nvmrc`

this file stores different version of `nvm`
By `nvm use {VERSION}` to use the setup version in `/.nvmrc`
[Usage](https://stackoverflow.com/questions/57110542/how-to-write-a-nvmrc-file-which-automatically-change-node-version)
```bash
$ echo "5.9" > .nvmrc
$ echo "lts/*" > .nvmrc # to default to the latest LTS version
$ echo "node" > .nvmrc # to default to the latest version
```
