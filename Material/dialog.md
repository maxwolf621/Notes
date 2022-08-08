# [Dialog](https://blog.angular-university.io/angular-material-dialog/)

[geek](https://www.geeksforgeeks.org/how-to-use-mat-dialog-in-angular/)  

## Declaring a Material Dialog body component
```typescript
import {MatDialogModule} from "@angular/material";

@NgModule({
    declarations: [
        ...
        CourseDialogComponent
    ],
    imports: [
        ...
        MatDialogModule
    ],
    providers: [
       ...
    ],
    bootstrap: [AppComponent],
    entryComponents: [CourseDialogComponent]
})
export class AppModule {
}
```
- `CourseDialogComponent` will be the body of our custom dialog.

## Creating and opening an Angular Material Dialog

via `openDialog` to create Dialog
```typescript
import {MatDialog, MatDialogConfig} from "@angular/material";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {

    /**
      * we are then creating an instance of MatDialogConfig, which will configure the dialog with a set of default behavior
      */
    constructor(private dialog: MatDialog) {}

    /**
      * Overriding a couple of those default behaviors. 
      * for example, we are 
      */
    openDialog() {

        const dialogConfig = new MatDialogConfig();
        
        /**
          * setting disableClose to true, 
          * which means that the user will not be able to close 
          * the dialog just by clicking outside of it
          */
        dialogConfig.disableClose = true;
        
        /**
          * we are also setting autoFocus to true, 
          * meaning that the focus will be set automatically 
          * on the first form field of the dialog
          */
        dialogConfig.autoFocus = true;

        this.dialog.open(CourseDialogComponent, dialogConfig);
    }
}
```

## Angular Material Dialog Configuration Options

The class MatDialogConfig allows us to define a lot of configuration options.  
Besides the two that we have overridden, here are some other commonly used Material Dialog options:

`hasBackdrop`  : defines if the dialog should have a shadow backdrop, that blocks the user from clicking on the rest of the UI while the dialog is opened (default is `true`)   
`panelClass`   : adds a list of custom CSS classes to the Dialog panel  
`backdropClass`: adds a list of custom CSS classes to the dialog backdrop  
`position`     : defines a starting absolute position for the dialog. For example, this would show the dialog in top left corner of the page, instead of in the center  
```
dialogConfig.position = {
    'top': '0',
    left: '0'
};
```
`direction`: defines if the elements inside the dialog are right or left justified. The default is left-to-right (ltr), but we can also specify right-to-left (rtl).
`closeOnNavigation`: this property defines if the dialog should automatically close itself when we navigate to another route in our single page application, which defaults to true.

The MatDialogConfig also provides the properties `width`, `height`, `minWidth`, `minHeight`, `maxWidth` and `maxHeight`

## 4th Building the Material Dialog body for HTML

![image](https://user-images.githubusercontent.com/68631186/132138310-bebbccf5-622a-4374-8003-c44b47e45369.png)

Let's now have a look at CourseDialogComponent. This is just a regular Angular component, as it does not have to inherit from any particular class or implement a dialog-specific interface.

The content of this component could also be anything, and there is no need to use any of the auxiliary Angular Material directives. 

We could build the body of the dialog out of plain HTML and CSS if needed.
But if we want the dialog to have the typical Material Design look and feel, we can build the template using the following directives:
```html
<h2 mat-dialog-title>{{description}}</h2>

<mat-dialog-content [formGroup]="form">
  
    <mat-form-field>
        <input matInput
                placeholder="Course Description"
               formControlName="description">
    </mat-form-field>
      ....
 
</mat-dialog-content>

<mat-dialog-actions>
    <button class="mat-raised-button"(click)="close()">Close</button>
    <button class="mat-raised-button mat-primary"(click)="save()">Save</button>
</mat-dialog-actions>
```
`mat-dialog-title`: This identifies the title of the dialog, in this case the "Angular For Beginners" title on top

`mat-dialog-content`: this container will contain the body of this dialog, in this case, a reactive form

`mat-dialog-actions`: this container will contain the action buttons at the bottom of the dialog


## Passing Input Data to the Material Dialog Component

Dialogs are often used to edit existing data.   
We can pass data to the dialog component by using the data property of the dialog configuration object.   

Going back to our AppComponent, here is how we can pass some input data to the dialog:
```typescript
openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    /**
      * pass data to the dialog component
      */
    dialogConfig.data = {
        id: 1,
        title: 'Angular For Beginners'
    };

    this.dialog.open(CourseDialogComponent, dialogConfig);
}
```

We can then **get a reference to this data object in `CourseDialogComponent` by using the `MAT_DIALOG_DATA` injectable**:
```typescript
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
    selector: 'course-dialog',
    templateUrl: './course-dialog.component.html',
    styleUrls: ['./course-dialog.component.css']
})
export class CourseDialogComponent implements OnInit {

    form: FormGroup;
    description:string;

    constructor(
        private fb: FormBuilder,
        
        /**
          * get a reference to data object
          */
        private dialogRef: MatDialogRef<CourseDialogComponent>,
        @Inject(MAT_DIALOG_DATA) data) {

          this.description = data.description;
    }

    ngOnInit() {
        this.form = fb.group({
            description: [description, []],
            ...
        });
    }

    save() {
        this.dialogRef.close(this.form.value);
    }

    close() {
        this.dialogRef.close();
    }
}
```

As we can see, the whole data object initially passed as part of the dialog configuration object can now be directly injected into the constructor.

We have also injected something else, a reference to the dialog instance named `dialogRef`.      
We will use it to close the dialog and pass output data back to the parent component.   

#### Closing The Dialog + Passing Output Data

Now that we have an editable form inside a dialog, we need a way to pass the modified (or new) data back to the parent component.

We can do via the `close()` method. We can call it without any arguments if we simply want to close the dialog:
```typescription
close() {
    this.dialogRef.close();
}
```

But we can also pass the modified form data back to AppComponent in the following way:
```typescript
save() {
    this.dialogRef.close(this.form.value);
}
```


```typescript
openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
        id: 1,
        title: 'Angular For Beginners'
    };

    /**
     * the call to dialog.open() returns a dialog reference, 
     * which is the same object injected in the constructor of CourseDialogComponent.
     */
    this.dialog.open(CourseDialogComponent, dialogConfig);
    
    const dialogRef = this.dialog.open(CourseDialogComponent, dialogConfig);

    /**
      * We can then use the dialog reference to subscribe to the afterClosed() observable, 
      * which will emit a value containing the output data passed to dialogRef.close().
      */
    dialogRef.afterClosed().subscribe(
        data => console.log("Dialog output:", data)
    );    
}
```
