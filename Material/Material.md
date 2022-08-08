

[`map-chip-list`](https://ithelp.ithome.com.tw/articles/10196270)

```htmlembbed
<mat-chip-list>
  <mat-chip selected="true">JavaScript</mat-chip>
  <mat-chip>Material Design</mat-chip>
  <mat-chip>Angular Material</mat-chip>
</mat-chip-list>
```
with `selected=true` , each slected `mat-chip` (e.g  the following image `java`) will have the status 
![image](https://user-images.githubusercontent.com/68631186/132796348-0b2d519c-d1ce-4913-b13d-cae6a8a42e49.png)


change the color of the `mat-chip`
```
<mat-chip color="primary" selected="true">JavaScript</mat-chip>
```

via CSS to custom the `mpt-chip` shape ... etc
```
.mat-basic-chip {
  background: lime;
  margin: 0 0 0 8px;
  padding: 7px 12px;
  border-radius: 5px;
}
```

`mat-chip` with close button on it


with even binding `(remove) = methodInYourComponent(tag)`
```html
<mat-chip-list>
  <mat-chip *ngFor="let tag of tags" (remove)="removeTag(tag)">
    {{ tag }}
    <mat-icon matChipRemove>cancel</mat-icon>
  </mat-chip>
</mat-chip-list>
```


```typescript
@Component({
  ...
})
export class AddPostDialogComponent implements OnInit {
  tags = ['JavaScript', 'Material Design', 'Angular Material'];
  
  /**
    * define remove tag method
    */
  removeTag(tagName) {
    this.tags = this.tags.filter(tag => tag !== tagName);
  }
```

give a attirbute of the `mat-chip` cant be removed or not with `removeable`
```
<mat-chip removable="false">
  ...
</mat-chip-list>
```


##　`<mat-chip-list>`包裝在`<mat-form-field>`

```typescript
import { ENTER, COMMA } from '@angular/cdk/keycodes';
@Component({
  ...
})
export class AddPostDialogComponent implements OnInit {
  tags = ['JavaScript', 'Material Design', 'Angular Material'];
  
  /**
    * receive the tags from user-input
    */
  separatorKeysCodes = [ENTER, COMMA];
  
  /**
    * add new tag (no duplicates)
    */
  addTag($event: MatChipInputEvent) {
    if (($event.value || '').trim()) {
      const value = $event.value.trim();
      
      /**
        * check if tag exists already
        */
      if (this.tags.indexOf(value) === -1) {
        this.tags.push(value);
      }
    }

    $event.input.value = '';
  }
}
```
- `input`：輸入的來源，基本上就是DOM
- `value`：輸入的資料

```typescript
<mat-form-field>
  <mat-chip-list #chipList>
    <mat-chip *ngFor="let tag of tags" (remove)="removeTag(tag)">
      {{ tag }}
      <mat-icon matChipRemove>cancel</mat-icon>
    </mat-chip>
  </mat-chip-list>
  <input placeholder="文章標籤" 
         [matChipInputFor]="chipList" 
         [matChipInputAddOnBlur]="true" 
         [matChipInputSeparatorKeyCodes]="separatorKeysCodes"
         (matChipInputTokenEnd)="addTag($event)" />
</mat-form-field>
```

在<input>中，關於chip有幾個重要的屬性可以設定

`[matChipInputFor]`：實際上要放置的`<mat-chip-list>`
`[matChipInputAddOnBlur]`：是否要在blur時加入chip
`[matChipInputSeparatorKeyCodes]`：當按下指定的鍵盤按鍵時，視為要新增chip
`(matChipInputTokenEnd)`：當真正要加入一個chip時的邏輯程式
  
## `Snackbar`

- Snackbar是在畫面最下方提供一個文字訊息，讓使用者知道目前系統大致的狀態。這種功能在Android中也叫toast。
- SnackBar主要由一個MatSnackBar service來控制顯示，要使用這個service，必須要先加入MatSnackBarModule。

```typescript
@Component({
  ...
})
export class AddPostConfirmDialogComponent implements OnInit {
  constructor(..., private snackBar: MatSnackBar) {}

  confirm() {
    ...
    /**
      * display Mission Compeleted
      * Press "i know now"
      */
    this.snackBar.open('Mission Completed', ' Press "I know Now"');
  }
}
```  

