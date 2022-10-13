# ReactiveFormsModule

- [ReactiveFormsModule](#reactiveformsmodule)
  - [Create A field for `<mat-input>`](#create-a-field-for-mat-input)
  - [Set default value of field](#set-default-value-of-field)
  - [FormGroup](#formgroup)
    - [getter in FormGroup](#getter-in-formgroup)

FormControl
- `FormControl` is the class that is used to `get` and `set` values and validation of the form control such as `<input>` and `<select>` tag.

FormGroup:
- FormGroup has the role of **tracking the value and validity state** of a group of FormControl.

[FormArray](https://appdividend.com/2019/12/16/angular-form-control-example/):
- FormArray **tracks the value and validity state of the array** of FormControl, FormGroup, or FormArray instances.

## Create A field for `<mat-input>`

```typescript
name = new FormControl('', [Validators.required]);
```
respects to  
```html
<input mat-input ... formControlName="name"  ... required>
```
- `required` means that this field ca not be skipped

## Set default value of field

```typescript
name = new FormControl('DefaultValue', [Validators.required]);
```

##　`setValue` method 

```typescript
this.name.setValue('setName'); 
```

## FormGroup

FormGroup tracks the value and validity state of a group of FormControl instances.

Initialize the group of form control for default value
```typescript
 angForm = new FormGroup({
    name: new FormControl('default_name', Validators.maxLength(10)),
    age: new FormControl(13, Validators.required),
    college: new FormControl('default_college'),
  });
```

### getter in FormGroup

```typescript
this.angForm.get('name').value
```


