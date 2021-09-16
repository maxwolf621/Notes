# ReactiveFormsModule

FormControl
- FormControl is the class that is used to `get` and `set` values and validation of the form control such as `<input>` and `<select>` tag.

FormGroup:
- FormGroup has the role of **tracking the value and validity state** of a group of FormControl.

[FormArray](https://appdividend.com/2019/12/16/angular-form-control-example/):
FormArray **tracks the value and validity state of the array** of FormControl, FormGroup, or FormArray instances.


## initialize

```typescript
name = new FormControl('', [Validators.required]);
```

respect to  
```html
<input [formControl]="name">
```

## set default value

```typescript


name = new FormControl('get default value', [Validators.required]);
```

##　`setValue`

```typescript
this.name.setValue('setName'); 
```

## FormGroup

FormGroup tracks the value and validity state of a group of FormControl instances.


Initialize the group of form control
```typescript
 angForm = new FormGroup({
    name: new FormControl('Krunal', Validators.maxLength(10)),
    age: new FormControl(26, Validators.required),
    college: new FormControl('VVP College'),
  });
```

### getter in FormGroup

```typescript
this.angForm.get('name').value
```


