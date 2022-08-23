# Template Literal Type

- [Template Literal Type](#template-literal-type)
  - [`Uppercase<StringType>` && `Lowercase<StringType>`](#uppercasestringtype--lowercasestringtype)
  - [`Capitalize<StringType>` && `Uncapitalize<StringType>`](#capitalizestringtype--uncapitalizestringtype)

```typescript 
type EmailLocaleIDs = "welcome_email" | "email_heading";
type FooterLocaleIDs = "footer_title" | "footer_sendoff";
 
type AllLocaleIDs = `${EmailLocaleIDs | FooterLocaleIDs}_id`;
```

AllLocaleIDs will be 
```typescript 
type AllLocaleIDs = "welcome_email_id" | "email_heading_id" | "footer_title_id" | "footer_sendoff_id"
```


```typescript
type AllLocaleIDs = `${EmailLocaleIDs | FooterLocaleIDs}_id`;
type Lang = "en" | "ja" | "pt";
 
type LocaleMessageIDs = `${Lang}_${AllLocaleIDs}`;
            
type LocaleMessageIDs = "en_welcome_email_id" | "en_email_heading_id" | "en_footer_title_id" | "en_footer_sendoff_id" | "ja_welcome_email_id" | "ja_email_heading_id" | "ja_footer_title_id" | "ja_footer_sendoff_id" | "pt_welcome_email_id" | "pt_email_heading_id" | "pt_footer_title_id" | "pt_footer_sendoff_id"
```


## `Uppercase<StringType>` && `Lowercase<StringType>`

```typescript
type ASCIICacheKey<Str extends string> = `ID-${Uppercase<Str>}`
type MainID = ASCIICacheKey<"my_app">
```
`MainID` type is 
```typescript
type MainID = "ID-MY_APP"
```

```typescript
type ASCIICacheKey<Str extends string> = `id-${Lowercase<Str>}`
type MainID = ASCIICacheKey<"MY_APP">
```
`MainID` type is
```typescript
type MainID = "id-my_app"
```

## `Capitalize<StringType>` && `Uncapitalize<StringType>`

`Capitalize` converts the first character in the string to an uppercase equivalent.    

`Uncapitalize` converts the first character in the string to a lowercase equivalent.    

```typescript 
type LowercaseGreeting = "hello, world";
type Greeting = Capitalize<LowercaseGreeting>;
        type Greeting = "Hello, world"


type UppercaseGreeting = "HELLO WORLD";
type UncomfortableGreeting = Uncapitalize<UppercaseGreeting>;
         type UncomfortableGreeting = "hELLO WORLD"
```
