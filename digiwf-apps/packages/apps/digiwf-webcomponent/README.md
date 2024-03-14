# DigiWF-WebComponents

This repository contains different WebComponents for integrating different DigiWF functionality into other applications.

Currently the following WebComponents are provided:
- `digiwf-service-instances-webcomponent`: Displays current Service Instance objects of a user in a list-style manner with pagination
- `digiwf-hello-world-webcomponent`: Show a simple "Hello World" message

## Usage

1. Add `script`-Import to webpage:

For `digiwf-service-instances-webcomponent`:
```html
<script
  src="hostname/loader-digiwf-service-instances-webcomponent.js"
></script>
```

For `digiwf-hello-world-webcomponent`:
```html
<script
  src="hostname/loader-digiwf-hello-world-webcomponent.js"
></script>
```

**Info:** `hostname` must be replaced with the hostname of the service that hosts the WebComponent as static files

2. Add Element to page with appropriate config

For `digiwf-service-instances-webcomponent`:
```html
<digiwf-service-instances-webcomponent digi-wf-base-url="ADD_HOSTNAME_OF_DIGIWF_GATEWAY_HERE" access-token-event-name="OPTIONAL"><digiwf-service-instances-webcomponent/>
```

For `digiwf-hello-world-webcomponent`:
```html
<digiwf-hello-world-webcomponent></digiwf-hello-world-webcomponent>
```

## WebComponent Properties

When adding these WebComponents to your website you can configure the behaviour by changing these properties
accordingly.

|                           | Description                                                                                        | Type   | Required | Default         |
|---------------------------|----------------------------------------------------------------------------------------------------|--------|----------|-----------------|
| `digi-wf-base-url`        | The URL of the DigiWF-Gateway, used for loading the data and redirecting when clicking UI elements | `String` | `true`    |                 |
| `access-token-event-name` | The name of the event used to retrieve an appropriate access token by the surrounding application  | `String` | `false`    | `access-token-loaded` |

## Events

### `access-token-loaded`

The WebComponent listens for this custom event to retrieve an access token used for authentication against DigiWF.
When the event occurs, the WebComponents start fetching data from the backend.
If a new event is fired (because a new access token should be used) the components reactively switches to the new access token automatically.

## Styling

The provided WebComponents expose different custom CSS variables that can be used to modify the look of the component by the surrounding application.
If a variable gets not provided, a specific default value will be used automatically.

The following CSS variables are provided:

|                               | Description                                                                                  | Default             |
|-------------------------------|----------------------------------------------------------------------------------------------|---------------------|
| `digiwf-webcomponent-color-primary`    | Sets the `background-color` property used for input elements (e.g. buttons, pagination)      | `#FC0`              |
| `digiwf-webcomponent-color-text`       | Sets the `color` property for all text-based HTML-tags                                       | `#444`              |
| `digiwf-webcomponent-color-icon`       | Sets the `color` property for all icons                                                      | `#FC0`              |
| `digiwf-webcomponent-color-background` | Sets the `background-color` property                                                         | `#FFF`              |
| `digiwf-webcomponent-color-hover`      | Sets the `background-color` property when hovering input elements (e.g. buttons, pagination) | `#FDB813`           |
| `digiwf-webcomponent-font-family`      | Sets the `font-family` property                                                              | `Arial, sans-serif` |
| `digiwf-webcomponent-border-radius`    | Sets the `border-radius` property of the surrounding container and all input elements        | `0px`               |
| `digiwf-webcomponent-shadow`           | Sets the `shadow` property of the surrounding container                                      | `0px`               |

To set those variables from outside the component you can either use

#### Setting CSS Variables via `root`-Selector: This will provide the variables to potential other WebComponents as well

```css
:root {
--digiwf-webcomponent-color-primary: orange;
}
```

#### Setting CSS Vaiables via WebComponent-Selector: This will provide the variables only to the specified WebComponent

e.g. for WebComponent `digiwf-service-instances-webcomponent`

```css
digiwf-service-instances-webcomponent {
--digiwf-webcomponent-color-primary: orange;
}
```

## Development Setup

1. Checkout
2. run `npm run dev`
3. Open `localhost:8085` or...
4. ...Integrate into your own dev-site alongside your WebComponent:

```html
<!-- Load webcomponent from local dev-server -->
<script
  src="http://127.0.0.1:8085/src/digiwf-service-instances-webcomponent.ts"
  type="module"
></script>

<digiwf-service-instances-webcomponent digi-wf-base-url="http://localhost:8085"></digiwf-service-instances-webcomponent>
```
