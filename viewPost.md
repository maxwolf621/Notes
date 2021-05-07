###### tags: `Spring Project`
# View//Create Post/Subreddit Page
[TOC]

Create Components of 
1. Create Post
2. Create Subreddit
3. Show All Posts
4. Show All Subreddits


## Add `CreatePost` / `Create Subreddit` page to Router

```typescript=
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
// .. other Components 
import { CreatePostComponent } from './post/create-post/create-post.component';
import { CreateSubredditComponent } from './subreddit/create-subreddit/create-subreddit.component';

const routes: Routes = [
  { path: 'create-post', component: CreatePostComponent },
  { path: 'create-subreddit', component: CreateSubredditComponent },
  // other Routes
];

//... 
```

## Side Bar Component in the 'Create Subreddit' page
```typescript=
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  goToCreatePost() {
    this.router.navigateByUrl('/create-post');
  }

  goToCreateSubreddit() {
    this.router.navigateByUrl('/create-subreddit');
  }

}
```


## HTML Component of Create-Subbreddit 

create-subreddit.component.html
```htmlembedded=
<div class="container">
  <div class="row">
    <div class="create-subreddit-container">
      <form class="post-form" [formGroup]="createSubredditForm" (ngSubmit)="createSubreddit()">
        <div class="form-group">
          <div class="create-subreddit-heading">
              Create Subreddit
          </div>
          <hr />
          <input type="text" [formControlName]="'title'" class="form-control" style="margin-top: 5px"
            placeholder="Title">
          <textarea type="text" [formControlName]="'description'" style="width: 100%; margin-top: 5px"
            placeholder="Description"></textarea>
          <div>
            <div style="margin-top: 5px" class="float-right">
              <button (click)="discard()" class="btnDiscard">Discard</button>
              <button (click)="createSubreddit()" class="btnCreateSubreddit">Create</button>
            </div>
          </div>
        </div>
      </form>
    </div>
    <div class="col-md-3">
      <div class="sidebar">
        <h5 class="guidelines">
            Posting to Spring Reddit
        </h5>
        <hr />
        <ul>
          <li>
              Remember the human
          </li>
          <hr />
          <li>
              Behave like you would in real life
          </li>
          <hr />
          <li>
              Don't spam
          </li>
          <hr />
        </ul>
      </div>
    </div>
  </div>
</div>
```

![](https://i.imgur.com/xnag1pI.png)

## ts component of create subreddit 

```typescript=
import { Component, OnInit } from '@angular/core';
// Receive the Input data from User
import { FormGroup, FormControl, Validators } from '@angular/forms';

// store the data of user input
import { SubredditModel } from '../subreddit-response';
// router to other pages
import { Router } from '@angular/router';
// cmmunicate with backend
import { SubredditService } from '../subreddit.service';

@Component({
  selector: 'app-create-subreddit',
  templateUrl: './create-subreddit.component.html',
  styleUrls: ['./create-subreddit.component.css']
})
export class CreateSubredditComponent implements OnInit {
  createSubredditForm: FormGroup;
  subredditModel: SubredditModel;
  title = new FormControl('');
  description = new FormControl('');

  /* 
   * Router to redirect the page
   * subredditService to data to backend
   */
  constructor(private router: Router, private subredditService: SubredditService) {
    // * get input data from User
    this.createSubredditForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.subredditModel = {
      name: '',
      description: ''
    }
  }

  ngOnInit() {
  }

  discard() {
    this.router.navigateByUrl('/');
  }
  // assign input data to backedEnd's subredditModel
  createSubreddit() {
    this.subredditModel.name = this.createSubredditForm.get('title').value;
    this.subredditModel.description = this.createSubredditForm.get('description').value;
    this.subredditService.createSubreddit(this.subredditModel).subscribe(data => {
      this.router.navigateByUrl('/list-subreddits');
    }, error => {
      console.log('Error occurred');
    })
  }
}
```

## Subreddit Service Component


Subredit Service Component mainly handles
- Display the All subreddits on webpage
    > get All Subreddits from Database
- Create A new Subreddit
    > Save it in Database

```typescript=
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
// this will cotains each subreddit's entity
import { SubredditModel } from './subreddit-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubredditService {
  constructor(private http: HttpClient) { }
   
  // **
  // Frontend asks to get 
  //     All subreddits from backend (Database)
  getAllSubreddits(): Observable> {
    return this.http.get>('http://localhost:8080/api/subreddit');
  }

  // ** FrontEnd Passes 
  //     create-subreddit to subredditModel
  createSubreddit(subredditModel: SubredditModel): Observable {
    return this.http.post('http://localhost:8080/api/subreddit',
      subredditModel);
  }
}
```

# Create Post

To create A Post we need
- Editor `TinyMCE` Dependency
- Create Create-Post `.ts` Payload 
- Create Create-Post Component 
- Create Create-Post HTML Component
- Create Create-Post Service Component
- Create Display-AllPosts HTML 

## Install Dependecy TinyMCE

Install the WYSIWYG Editor `TinyMCE`.
```bash
$ npm install --save @tinymce/tinymce-angula
```

## add `TinyMCE` to AppModule

app-module.ts
```typescript
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';

// Root Component and Other Components web page needs
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { TokenInterceptor } from './token-interceptor';
import { HomeComponent } from './home/home.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { PostTileComponent } from './shared/post-tile/post-tile.component';
import { VoteButtonComponent } from './shared/vote-button/vote-button.component';
import { SideBarComponent } from './shared/side-bar/side-bar.component';
import { SubredditSideBarComponent } from './shared/subreddit-side-bar/subreddit-side-bar.component';
import { CreateSubredditComponent } from './subreddit/create-subreddit/create-subreddit.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { ListSubredditsComponent } from './subreddit/list-subreddits/list-subreddits.component';
import { EditorModule } from '@tinymce/tinymce-angular';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    LoginComponent,
    HomeComponent,
    PostTileComponent,
    VoteButtonComponent,
    SideBarComponent,
    SubredditSideBarComponent,
    CreateSubredditComponent,
    CreatePostComponent,
    ListSubredditsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FontAwesomeModule,
    EditorModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

## Post Payload

create-post-payload.ts
```typescript
export class CreatePostPayload {
    postName: string;
    subredditName?: string;
    url?: string;
    description: string;
}
```

## HTML component of CREATE POST


```htmlembedded=
<div class="container">
  <div class="row">
    <hr />
    <div class="create-post-container col-md-9">
      <form class="post-form" [formGroup]="createPostForm" (ngSubmit)="createPost()">
        <div class="form-group">
          <div class="create-post-heading">
              Create Post
          </div>
          <hr />
          <input type="text" [formControlName]="'postName'" class="form-control" style="margin-top: 5px"
            placeholder="Title">

          <input type="text" class="form-control" [formControlName]="'url'" style="margin-top: 5px" placeholder="URL">

          <select class="form-control" style="margin-top: 10px" [formControlName]="'subredditName'">
            <option value="" selected disabled>Select Subreddit</option>
            <option *ngFor="let subreddit of subreddits">{{subreddit.name}}</option>
          </select>

          <editor [formControlName]="'description'" [init]="{
                      height: 500,
                      menubar: false,
                      plugins: [
                        'advlist autolink lists link image charmap print preview anchor',
                        'searchreplace visualblocks code fullscreen',
                        'insertdatetime media table paste code help wordcount'
                      ],
                      toolbar:
                        'undo redo | formatselect | bold italic backcolor | \
                        alignleft aligncenter alignright alignjustify | \
                        bullist numlist outdent indent | removeformat | help'
                    }"></editor>
          <span>
            <div style="margin-top: 5px" class="float-right">
              <button (click)="discardPost()" class="btnDiscard">Discard</button>
              <button (click)="createPost()"
class="btnCreatePost">Post</button>
            </div>
          </span>
        </div>
      </form>
    </div>
    <div class="col-md-3">
      <guidelines></guidelines>
      <about></about>
    </div>
  </div>
</div>

```

## Component

- `createPost()` method which first reads the FormControl values and creates the CreatePostPayload object. Once we have the necessary data, we call the createPost() method inside the SubredditService, we subscribe to the response

```typescript=
import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { SubredditModel } from 'src/app/subreddit/subreddit-response';
import { Router } from '@angular/router';
import { PostService } from 'src/app/shared/post.service';
import { SubredditService } from 'src/app/subreddit/subreddit.service';
import { CreatePostPayload } from './create-post-payload';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup;
  postPayload: CreatePostPayload;
  subreddits: Array;

  constructor(private router: Router, private postService: PostService,
    private subredditService: SubredditService) {
    this.postPayload = {
      postName: '',
      url: '',
      description: '',
      subredditName: ''
    }
  }

  ngOnInit() {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      subredditName: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.subredditService.getAllSubreddits().subscribe((data) => {
      this.subreddits = data;
    }, error => {
      throwError(error);
    });
  }

    
  createPost() {
    // create A payload via input form from HTML
    this.postPayload.postName = this.createPostForm.get('postName').value;
    this.postPayload.subredditName = this.createPostForm.get('subredditName').value;
    this.postPayload.url = this.createPostForm.get('url').value;
    this.postPayload.description = this.createPostForm.get('description').value;
    //  handling payload to backend
    this.postService.createPost(this.postPayload).subscribe((data) => {
      // if it success 
      this.router.navigateByUrl('/');
    }, error => {
      throwError(error);
    })
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }

}
```


# View Post Component

```bash=
ng g c post/view-post
```

## Component 
- Add a click directive and calling the method `goToPost()` with input as `post.id`

Inside the post-tile.component.ts we first injected the Router class from @angular/router and added the method goToPost() where we are navigating to the URL ‘/view-post/:id”


```typescript=
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { PostService } from '../post.service';
import { PostModel } from '../post-model';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class PostTileComponent implements OnInit {

  posts$: Array;
  faComments = faComments;

  constructor(private postService: PostService, private router: Router) {
    this.postService.getAllPosts().subscribe(post => {
      this.posts$ = post;
    });
  }

  ngOnInit(): void {
  }

  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }
}
```

## HTML component of VIEWING POST
```htmlembedded=
<!-- Display Posts-->
<div class="row post" *ngFor="let post of posts$">
  <app-vote-button [post]="post"></app-vote-button>
  <!-- Section to Display Post Information-->
  <div class="col-md-11">
    <span class="subreddit-info">
      <span class="subreddit-text"><a class="posturl" routerLink="">{{post.subredditName}}</a></span>
      <span> . Posted by <a class="username" routerLink="/user/{{post.userName}}">{{post.userName}}</a></span>
      <span> . {{post.duration}}</span>
    </span>
    <hr />
    <div class="post-title">
      <a class="postname" href="{{post.url}}">{{post.postName}}</a>
    </div>
    <div>
      <p class="post-text" [innerHtml]="post.description"></p>
    </div>
    <hr />
    <span>
      <a class="btnCommments" role="button">
        <fa-icon [icon]="faComments"></fa-icon>
        Comments({{post.commentCount}})
      </a>
      <button class="login" (click)="goToPost(post.id)">
        Read Post
      </button>
    </span>
  </div>
</div>
```



## Add view-post to Router
```typescript=
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
// Components
import { SignupComponent } from './auth/signup/signup.component';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { CreateSubredditComponent } from './subreddit/create-subreddit/create-subreddit.component';
import { ListSubredditsComponent } from './subreddit/list-subreddits/list-subreddits.component';
import { ViewPostComponent } from './post/view-post/view-post.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'view-post/:id', component: ViewPostComponent },
  { path: 'list-subreddits', component: ListSubredditsComponent },
  { path: 'create-post', component: CreatePostComponent },
  { path: 'create-subreddit', component: CreateSubredditComponent },
  { path: 'sign-up', component: SignupComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

## HTML component of view-post

```htmlembedded
<div class="container">
  <div class="row">
    <hr />
    <div class="col-md-9">
      <div class="row post">
        <div class="col-md-1">
        <!-- COMPONENT -->
          <app-vote-button [post]="post"></app-vote-button>
        </div>
        <div class="col-md-11">
          <span>
            <span class="subreddit-text"><a class="post-url" href="">{{post.subredditName}}</a></span>
            <span> . Posted 
                <span> {{post.duration}} </span>
                by
              <a *ngIf="post.userName === null" class="username" href="">Anonymous</a>
              <a *ngIf="post.userName != null" class="username" href="">{{post.userName}}</a>
            </span>                        
          </span>
          <hr />
          <a routerLink="post.url" class="post-title">{{post.postName}}</a>
          <div>
            <p class="post-text" [innerHtml]="post.description"></p>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <sidebar></sidebar>
      <!-- COMPONENT -->
      <sidebar-view-subreddit></sidebar-view-subreddit>
    </div>
  </div>
</div>
```

## Component

```typescript
import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/shared/post.service';
import { ActivatedRoute } from '@angular/router';
import { PostModel } from 'src/app/shared/post-model';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  postId: number;
  post: PostModel;

  constructor(private postService: PostService, private activateRoute: ActivatedRoute) {
    // get post id using activateRoute.snapshot.param
    this.postId = this.activateRoute.snapshot.params.id;
    // using post id to get more details about the post
    //    return Observable<PostModel>
    //        We subscribe to the response and assign the response, to the post variable
    this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
    }, error => {
      throwError(error);
    });
  }

  ngOnInit(): void {
  }
}
```

## HTML component of view-post

```htmlembedded=

view-post.component.html

<div class="container">
  <div class="row">
    <hr />
    <div class="col-md-9">
      <div class="row post">
        <div class="col-md-1">
          <app-vote-button [post]="post"></app-vote-button>
        </div>
        <div class="col-md-11">
          <span>
            <span class="subreddit-text"><a class="post-url" href="">{{post.subredditName}}</a></span>
            <span> . Posted
              <span> {{post.duration}} </span>
              by
              <!-- show Anonymous or usename-->
              <a *ngIf="post.userName === null" class="username" href="">Anonymous</a>
              <a *ngIf="post.userName != null" class="username" href="">{{post.userName}}</a>
            </span>
          </span>
          <hr />
          <a routerLink="post.url" class="post-title">{{post.postName}}</a>
          <div>
            <p class="post-text" [innerHtml]="post.description"></p>
          </div>
          <div class="post-comment">
          <!-- Post Comment -->
            <form [formGroup]="commentForm" (ngSubmit)="postComment()">
              <div class="form-group">
                <textarea class="form-control" [formControlName]="'text'" placeholder="Your Thoughts?"></textarea>
              </div>
              <button type="submit" class="login float-right">Comment</button>
            </form>
          </div>
          <div style="margin-top: 60px;" *ngFor="let comment of comments">
            <div class="comment">
              <div class="username">
                <a routerLink="/user/comment.username">{{comment.userName}}</a>
              </div>
              <div>
                <p>{{comment.duration}}</p>
              </div>
              <b>{{comment.text}}</b>
            </div>
            <hr />
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <sidebar></sidebar>
      <sidebar-view-subreddit></sidebar-view-subreddit>
    </div>
  </div>
</div>
```


