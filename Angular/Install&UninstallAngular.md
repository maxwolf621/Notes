[Uninstall&Install Angular](https://github.com/angular/angular-cli/issues/1190)  
[Cache Clean](https://stackoverflow.com/questions/39566257/how-to-uninstall-upgrade-angular-cli)  
[node_module](https://stackoverflow.com/questions/63294260/what-is-the-purpose-of-node-modules-folder)  
[To install Node.js](https://hackmd.io/6Nvu-p8aQ0ynRhhsppBbww)  


## Reinstall angular  

```bash
# old version
npm uninstall -g @angular/cli

# delete cache
npm cache verify

# install latest angular cli version
npm install -g @angular/cli@latest

# create (dependencies)node_module's fold and package.lock-json
npm install
```

Angular will be installed in this directory
- node_modules contains dependencies of `packages.json` needs 
```bash 
/usr/lib/node_modules
```

## `package.json`

Create a `package.json` for the local npm package
[ref1](https://www.sitepoint.com/npm-guide/)  
[ref2](https://stackoverflow.com/questions/9484829/npm-cant-find-package-json)  

```bash
npm init
```

`npm ERR! enoent ENOENT: no such file or directory, open '/usr/lib/node_modules/package.json'`
```bash
# Following the below steps you well get package.json file.
npm --version
npm install express
npm init -y
```

## ERRORS

[`npm WARN ... requires a peer of ... but none is installed. You must install peer dependencies yourself`](https://stackoverflow.com/questions/38817571/npm-install-multiple-package-names)    
Delete `node_module` file and `package-lock.json` and redo 
```bash
npm install
```

- [How to install npm peer dependencies automatically?](https://stackoverflow.com/questions/35207380/how-to-install-npm-peer-dependencies-automatically#comment97140066_35207380)   
- [An unhandled exception occurred: Cannot find module `'@angular-devkit/build-angular/package.json'`](https://reurl.cc/d270nV)   

## install `node`

```bash
nvm install node
# "node" is an alias for the latest version
```

## (Update dependencies)[https://angular.io/cli/update]

https://stackoverflow.com/questions/10068592/how-do-i-update-devdependencies-in-npm
```console
npm install -g npm-check-updates
ncu -u
npm install
```

## install the dependecies

```
# install dependency to package.json
ng i new_dependency

# 
ng install
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
