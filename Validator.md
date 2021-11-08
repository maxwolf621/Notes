# Custom Validator


[Note Taking From](https://stackoverflow.com/questions/51605737/confirm-password-validation-in-angular-6)


## Step 1 create custom validator via `ValidatorFn`

`VALIDATOR_NAME : validatorFn = (group : AbstractControl) : ValidationErrors | null => { ... }`

```typescript
checkPasswords: ValidatorFn = (group: AbstractControl) :  ValidationErrors | null => { 
  // fields to be check
  let pass = group.get('password').value;
  let confirmPass = group.get('confirmPassword').value
  
  // how to check the fields
  return pass === confirmPass ? null : { notSame: true }
}
```

## Step 2 Create `fromGroup` in `.ts` and add our custom Validator

```typescript
this.myForm = this.fb.group({
  password: ['', [Validators.required]],
  confirmPassword: ['']
}, { validators: this.checkPasswords }) // call our custom validator
```

## Step 3 Create A component that controls when to show ERROR notification

the `<mat-error>` in template only shows if a `FormControl`'s fields is invalid, so you need an error state matcher `< ... [errorStateMatcher]= MATCHER_FIELD_NAME>`:

```typescript
export class MyErrorStateMatcher implements ErrorStateMatcher {
    
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
        
        // invalid and dirty 
        const invalidCtrl = !!(control?.invalid && control?.parent?.dirty);
        
        const invalidParent = !!(control?.parent?.invalid && control?.parent?.dirty);
        
        return invalidCtrl || invalidParent;
    }
}
```
- in the above you can tweak when to show error message. 
    > I would only show message when the password field is touched.   
    > Also I would like above, remove the required validator from the confirmPassword field, since the form is not valid anyway if passwords do not match.  

Create a new `ErrorStateMatcher` in `.ts`
```typescript 
matcher = new MyErrorStateMatcher();
```

## Step 4 Add Validators to Template

```html
<form [formGroup]="myForm">

  <mat-form-field>
    <input matInput placeholder="New password" formControlName="password" required>
    <mat-error *ngIf="myForm.hasError('required', 'password')">
      Please enter your new password
    </mat-error>
  </mat-form-field>

  <mat-form-field>
    <input matInput placeholder="Confirm password" formControlName="confirmPassword" [errorStateMatcher]="matcher">
    <mat-error *ngIf="myForm.hasError('notSame')">
      Passwords do not match
    </mat-error>  
  </mat-form-field>

</form>
```