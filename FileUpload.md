
[Reference](https://stackoverflow.com/questions/47936183/angular-file-upload)
[GeeksForGeeks](https://www.geeksforgeeks.org/angular-file-upload/)
[poiemaweb](https://poiemaweb.com/angular-file-upload)
[multiple files](https://stackoverflow.com/questions/48343853/angular-5-how-to-upload-an-image)

3 steps 

### 1. template in `.html`

1. Create `<form input type="file" id=file #uploader ...>` to input the selected file
2. Create A button to upload the file `<button ... (click)=uploader.click()> ..</button>`

[Trigger](https://stackoverflow.com/questions/48444168/how-to-trigger-the-file-upload-input-in-angular/48444372)   

```html
<!-- form -->
<div class="form-group">
    <label for="file">Choose File</label>
    <input type="file"
           id="file"
           #uploader
           (change)="handleFileInput($event.target.files)">
</div>

<!-- open upload dialog  -->
<button mat-icon-button (click)=uploader.click()>Upload Your File</button>
```
- In form input we get event binding : `(change) = Input Method For File Upload($event.target.files)` or `(change) = Input Method For File Upload($event)`


### 2. handing file-upload in `.ts`

- Selected-File type : `FileList` or `File`

```typescript
// declare (fileToUpload is an array)
fileToUpload: File | null = null;

construct(this fileServer : FileServer){}

// get file object from html
handleFileInput(files: FileList) {

    // item(0) : only upload one files
    this.fileToUpload = files.item(0);
}

// send file to backend
Upload(){
    this.fileService.postFile(this.fileToUpload).subscribe(
        (reuse) =>{
            //...
        },  //...
    )
}
```

if `(change)= handleFileInput($event)` then
```typescript
// we need to fetch the uploaded-file object
handleFileInput($event : any){
    this.file = event.target.files[0];
}
```

### 3. service for file uploading 

create `formData` to send file to backend

```typescript
postFile(fileToUpload: File): Observable<boolean> {
    
    const endpoint = 'your-destination-url';

    const formData: FormData = new FormData();

    formData.append('fileKey', fileToUpload, fileToUpload.name);
    
    return this.httpClient
      .post(endpoint, formData, { headers: yourHeadersConfig })
      .map(() => { return true; })
      .catch((e) => this.handleError(e));
}
```


Upload Multiple Files
```typescript
public files: any[];

constructor(private http : httpClient) { 
    this.files = []; 
}

handleFileInput($event: any) {
  this.files = event.target.files;
}

// send to backend
onUpload() {

  const formData = new FormData();
  
  for (const file of this.files) {
      // configure each formData of each uploaded file 
      formData.append(name, file, file.name);
  }
  this.http.post('url', formData).subscribe(x => ....);
}
```

### [Send uploaded-file as `FormData` or Binary data to Backend](https://academind.com/tutorials/angular-image-upload-made-easy)

```typescript


// send via binary
onUpload() {
  this.http.post('my-backend.com/file-upload', this.selectedFile)
    .subscribe(...);
}

// send via FormData
onUpload() {
  const uploadData = new FormData();
  uploadData.append('myFile', this.selectedFile, this.selectedFile.name);
  
  this.http.post('my-backend.com/file-upload', uploadData)
    .subscribe(...);
}
```

### listen 

```typescript
onUpload() {
  ...
  this.http.post('my-backend.com/file-upload', uploadData, {
    reportProgress: true,
    observe: 'events'
  }).subscribe(event => {
      console.log(event); // handle event here
    });
}
```


## Use `FormGroup` to handle file upload procession

path for `<img src>`
```typescript
<img [src]="imageSrc" [alt]="imageAlt" />

<img src="{{imageSrc}}" alt="{{imageAlt}}" />
```

```html
<div class="container">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
            
            <h1 class="text-center">Angular File upload</h1>

            <form [formGroup]="form" (ngSubmit)="onSubmit(fileInput.files)">
                
                <div class="form-group">
                    <img *ngIf="imageSrc" [src]="imageSrc" class="avatar">
                    <div class="btns clearfix">

                        <label class="btn btn-file btn-cancel pull-left">
                            Pick an image      
                            <input type="file" accept="image/*"
                            (change)="onFileChange(fileInput.files)" #fileInput>
                        </label>

                        <button type="submit" class="btn btn-save pull-right"
                                [disabled]="form.invalid || loading">
                                Save
                                <i class="fa fa-spinner fa-spin fa-fw" *ngIf="loading"></i>
                        </button>
                    </div>
                </div>
            </form>
            
            <pre>{{ form.value | json }}</pre>
            <pre>{{ result | json }}</pre>
        
        </div>
    </div>
</div>
  
```

```typescript
import { Component } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent {
  
  // file to backend
  apiUrl = 'http://localhost:8080';


  form: FormGroup;
  loading = false;

  //default image
  imageSrc = '/assets/images/github-cat.jpeg';

  result;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient) {

    this.form = this.fb.group({
      avatar: ['', Validators.required]
    });

  }

  onFileChange(files: FileList) {
    if (files && files.length > 0) {
      
      const file = files[0];

      /**
       * For Preview
       */
      const reader = new FileReader();

      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imageSrc = reader.result;
      };

      //reactive form input[type="file"]
      this.avatar.setValue(file.name);
    }
  }

  // upload data to backend
  onSubmit(files: FileList) {
    
    const formData = new FormData();
    
    formData.append('avatar', files[0]);

    this.loading = true;
    
    // payload to backed
    console.log(formData.get('avatar'));

    this.http.post(`${this.apiUrl}/upload`, formData).subscribe
    (res => {
        this.result = res;
        this.loading = false;
        this.avatar.setValue(null);
        }
    );
  }

  get avatar() {
    return this.form.get('avatar');
  }
}
```



### [FileReader](https://www.joyk.com/dig/detail/1626190019825120)  

https://stackblitz.com/edit/angular-file-upload-preview-k6dnu5?file=app%2Fapp.component.ts

```html
<div class="columns">
  <div class="column is-one-third">

      <!-- form to update the file -->
      <div class="field">

          <div class="file has-name is-primary is-fullwidth">
              <label class="file-label">
                  <input class="file-input"
                        type="file"
                        name="file"
                        #fileInput
                        (change)="onChange(fileInput.files[0])"/>
                        
                        <span class="file-cta">
                            <span class="file-icon">
                                <fa-icon [icon]="['fas', 'upload']"></fa-icon>
                            </span>
                            <span class="file-label">Choose a file…</span>
                        </span>

                    <span class="file-name">{{ fileName }} </span>
                </label>
            </div>

        </div>

        <!-- progress -->
        <div class="field" *ngIf="!infoMessage">
            <div class="control">
                <progress class="progress is-primary"
                [attr.value]="progress"
                max="100"></progress>
            </div>
        </div>
        
        <!-- upload button -->
        <div class="field">
            <div class="control">
                <button
                class="button is-primary"
                (click)="onUpload()"
                [attr.disabled]="isUploading ? '' : null"
                >Upload</button>
            </div>
        </div>
    </div>

    <!-- image -->
    <div class="column">
        <figure class="image is-128x128">
            <img [src]="imageUrl" />
        </figure>
    </div>
</div>

```

```typescript
import { Component, OnInit } from "@angular/core";
import { UploaderService } from "../services/uploader.service";

@Component({
  selector: "app-user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.component.scss"]
})
export class UserComponent implements OnInit {
    
    progress: number;
  
    infoMessage: any;
    isUploading: boolean = false;
    
    // file object we selected from html     
    file: File;

    imageUrl: string | ArrayBuffer = "https://bulma.io/images/placeholders/480x480.png";
    
    fileName: string = "No file selected";

    constructor(private uploader: UploaderService) {}

    ngOnInit() {
        
        // progress bar
        this.uploader.progressSource.subscribe(progress => {
            this.progress = progress;
        });
    }
    
    // Method : fetch the selected file $event
    onChange(file: File) {
        if (file) {
            this.fileName = file.name;
            this.file = file;

            // FileReader
            const reader = new FileReader();
            reader.readAsDataURL(file);

            reader.onload = event => {
                this.imageUrl = reader.result;
            };
        }
    }

    // send selected file to backend
    onUpload() {

        this.infoMessage = null;

        // progress star from 0%
        this.progress = 0;
        
        // attr.disable if isUploading is true
        this.isUploading = true;
        
        // upload server (backend)
        this.uploader.upload(this.file).subscribe(message => {
            this.isUploading = false;
            this.infoMessage = message;
      });
    }
}

/**
 *  Server 
 */
import { Injectable } from "@angular/core";
import { HttpClient, HttpRequest, HttpEventType,HttpEvent} from "@angular/common/http";
import { map, tap, last } from "rxjs/operators";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class UploaderService {
    public progressSource = new BehaviorSubject<number>(0);

    constructor(private http: HttpClient) {}


    // Method : Send file to backend
    upload(file: File) {

      // configure formData type
      let formData = new FormData();
      formData.append("avatar", file);


      // request that send to backend
      const req = new HttpRequest(
        "POST",
        "http://localhost:5000/upload",
        formData,
        {
          reportProgress: true
        }
      );

      return this.http.request(req).pipe(
        map(event => this.getEventMessage(event, file)),
        tap((envelope: any) => this.processProgress(envelope)),
        last()
      );
    }
    
    // progress bar
    processProgress(envelope: any): void {
      if (typeof envelope === "number") {
        this.progressSource.next(envelope);
      }
    }

    /**
     * events
     *   HttpEventType.sent
     *   HttpEventType.UploadProgress
     *   HttpEventType.Response
     */ 
    private getEventMessage(event: HttpEvent<any>, file: File) {
      switch (event.type) {
        case HttpEventType.Sent:
          return `Uploading file "${file.name}" of size ${file.size}.`;
        case HttpEventType.UploadProgress:
          return Math.round((100 * event.loaded) / event.total);
        case HttpEventType.Response:
          return `File "${file.name}" was completely uploaded!`;
        default:
          return `File "${file.name}" surprising upload event: ${event.type}.`;
      }
    }
}
```

