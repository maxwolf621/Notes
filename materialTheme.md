# Material Theme

- [Custom Typography](https://indepth.dev/tutorials/angular/angular-material-theming-system-complete-guide#modify-typography)

- [Material Theme](#material-theme)
  - [The core mixin](#the-core-mixin)
  - [`@angular/material#define-palette` & `@angular/material#define-[light|dark]-theme`](#angularmaterialdefine-palette--angularmaterialdefine-lightdark-theme)
  - [palette](#palette)
    - [Custom Palette](#custom-palette)
  - [`mixin` that applying a theme to components](#mixin-that-applying-a-theme-to-components)
    - [Material Component's SCSS](#material-components-scss)
    - [Apply Angular Material’s theme to custom component](#apply-angular-materials-theme-to-custom-component)
  - [Output the Layout to VIEW](#output-the-layout-to-view)
  - [Usage](#usage)

## The core mixin

```scss
@use "@angular/material" as mat;

@include mat.core();
```
- Angular Material defines a mixin named core that includes prerequisite styles for common features used by multiple components


## `@angular/material#define-palette` & `@angular/material#define-[light|dark]-theme`

To construct a theme, 2 palettes are required: primary and accent, and warn palette is optional.

- Each value in palette is called a `hue`. 
- In Material Design, each hue in a palette has an identifier number. These identifier numbers include `50`, and then each `100` value between `100` and `900`. The numbers order hues within a palette from lightest to darkest. 
- Angular Material represents a palette as a SASS map.
```scss
// The define-palette SASS function accepts a color palette, as well as 4 optional hue numbers.
$my-app-primary: mat.define-palette(mat.$indigo-palette);
$my-app-accent: mat.define-palette(mat.$pink-palette, A200, A100, A400);
$my-app-warn: mat.define-palette(mat.$red-palette);

// calling either #define.light-theme or #define.dark-theme determines background 
$my-app-theme: mat.define-light-theme(
  (
    color: (
      primary: $my-app-primary,
      accent: $my-app-accent,
      warn: $my-app-warn,
    ),
    /**
    typography: $custom-typography,
    */
  )
);
```
## palette

predefined palettes that supported by material looks like the following 
```scss
$red-palette: (
  50: #ffebee,
  100: #ffcdd2,
  200: #ef9a9a,
  300: #e57373,
  // ...
  contrast: (
    50: $dark-primary-text,
    100: $dark-primary-text,
    200: $dark-primary-text,
    300: $dark-primary-text,
    // ...
  )
);
```

### Custom Palette

```scss
$indigo-palette: (
 50: #e8eaf6,
 100: #c5cae9,
 200: #9fa8da,
 300: #7986cb,
 // ... continues to 900
 contrast: (
   50: rgba(black, 0.87),
   100: rgba(black, 0.87),
   200: rgba(black, 0.87),
   300: white,
   // ... continues to 900
 )
);
```

## `mixin` that applying a theme to components


- `@mixin all-component-themes($themeName)` : emits styles for both color(`all-component-colors`) and typography(`all-component-typographies`)
  - `@mixin all-component-colors($config-or-theme)` : emits all components' color styles
  - `@mixin all-component-typographies($config-or-theme: null)` : emits all components’ typography styles

### Material Component's SCSS

Each Angular Material component contains a color, a typography and a theme mixin.
```scss
// src/material/button/_button-theme.scss
// content reduced for brevity

@mixin color($config-or-theme) {
    
    $config: theming.get-color-config($config-or-theme);
    $primary: map.get($config, primary);
    $accent: map.get($config, accent);
    $warn: map.get($config, warn);
  
    // sets up color for buttons
}

@mixin typography($config-or-theme) {
    $config: typography.private-typography-to-2014-config(
        theming.get-typography-config($config-or-theme));
        
    .mat-button, .mat-raised-button, .mat-icon-button, .mat-stroked-button,
    .mat-flat-button, .mat-fab, .mat-mini-fab {
        font: {
            family: typography-utils.font-family($config, button);
            size: typography-utils.font-size($config, button);
            weight: typography-utils.font-weight($config, button);
        }
    }
}

@mixin theme($theme-or-color-config) {
    $theme: theming.private-legacy-get-theme($theme-or-color-config);
    
    @include theming.private-check-duplicate-theme-styles($theme, 'mat-button') {
        $color: theming.get-color-config($theme);
        $typography: theming.get-typography-config($theme);
        @if $color != null {
            @include color($color);
        }
        @if $typography != null {
            @include typography($typography);
        }
    }
}
```
To style Material Button 
```scss
// @include mat.all-component-themes($my-app-theme); <-- removed
@include mat.core-theme($my-app-theme);
@include mat.button-theme($my-app-theme);
```
- `core-theme` emits theme-dependent styles for common features used across multiple components

### Apply Angular Material’s theme to custom component

```scss

```

## Output the Layout to VIEW

In `index.html`
```html
<body>
    <!-- 
        This header will *not* 
        be styled because it is outside 
        `.mat-typography` 
    -->
    <h1>Top header (Material Typography doesn't apply here)</h1>
    <!-- 
        This paragraph will be styled 
        as `body-1` via the `.mat-body` 
        CSS class applied 
    -->
    <p class="mat-body">Introductory text</p>

    <!--
        Custom theme will style 
        the following <div>..</div> block
    -->
    <div class="mat-typography mat-app-background">
      <app-root></app-root>
    </div>
</body>
```

## Usage 

```scss 
@mixin _theme-property($theme, $property, $hue) {
  $primary: map.get($theme, primary);
  $accent: map.get($theme, accent);
  $warn: map.get($theme, warn);
  $background: map.get($theme, background);
  $foreground: map.get($theme, foreground);

  // & in css : xxxx.mat-primary
  &.mat-primary {
    #{$property}: theming.get-color-from-palette($primary, $hue);
  }
  &.mat-accent {
    #{$property}: theming.get-color-from-palette($accent, $hue);
  }
  &.mat-warn {
    #{$property}: theming.get-color-from-palette($warn, $hue);
  }

  &.mat-primary,
  &.mat-accent,
  &.mat-warn,
  &.mat-button-disabled {
    &.mat-button-disabled {
        // $property=="color" ? $foreground : $background
        $palette: if($property == "color", $foreground, $background);
        // string interpolation ($property => "property")
        #{$property}: theming.get-color-from-palette($palette, disabled-button);
    }
  }
}

@mixin color($config-or-theme) {
    // Map#get
    $config: theming.get-color-config($config-or-theme);
    $foreground: map.get($config, foreground);
    .mat-button,
    .mat-icon-button,
    .mat-stroked-button {
        @include _theme-property($config, "color", text);
    }
}
```

