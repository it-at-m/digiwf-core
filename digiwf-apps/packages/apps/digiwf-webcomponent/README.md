# DigiWF-WebComponents

This repository contains different WebComponents for integrating different DigiWF functionality into other applications.

Currently, the following WebComponents are provided:

- `digiwf-service-instances-webcomponent`: Displays current Service Instance objects of a user in a list-style manner with pagination
- `digiwf-hello-world-webcomponent`: Show a simple "Hello World" message

## Usage

### 1. Add `script`-Import to webpage:

For `digiwf-service-instances-webcomponent`:

```html
<script src="HOST/loader-digiwf-service-instances-webcomponent.js"></script>
```

For `digiwf-hello-world-webcomponent`:

```html
<script src="HOST/loader-digiwf-hello-world-webcomponent.js"></script>
```

**Info:** `HOST` must be replaced with the hostname (and eventually an additional path) of the service that hosts the WebComponent as static files

### 2. Add element to page with appropriate config

For `digiwf-service-instances-webcomponent`:

```html
<digiwf-service-instances-webcomponent
  access-token-event-name="OPTIONAL"
></digiwf-service-instances-webcomponent>
```

For `digiwf-hello-world-webcomponent`:

```html
<digiwf-hello-world-webcomponent></digiwf-hello-world-webcomponent>
```

**Info:** Supplying the custom properties is optional as described in [WebComponent Properties](#webcomponent-properties)

## WebComponent Properties

When adding these WebComponents to your website you can configure the behaviour by changing these properties
accordingly.

|                           | Description                                                                                                                  | Type     | Required | Default               |
| ------------------------- | ---------------------------------------------------------------------------------------------------------------------------- | -------- | -------- | --------------------- |
| `access-token-event-name` | The name of the event used to retrieve an appropriate access token by the surrounding application                            | `String` | `false`  | `access-token-loaded` |
| `page-size`               | The amount of elements to display per page                                                                                   | `number` | `false`  | `4`                   |
| `max-pages-visible`       | The amount of pages at maximum shown in the pagination                                                                       | `number` | `false`  | `5`                   |
| `root-heading-level`      | The level of HTML `h`-element used at component root (to correctly integrate into surrounding application for accessibility) | `number` | `false`  | `1`                   |

## Events

### `access-token-loaded`

The WebComponent listens for this custom event to retrieve an access token used for authentication against DigiWF.
When the event occurs, the WebComponents start fetching data from the backend.
If a new event is fired (because a new access token should be used) the components reactively switches to the new access token automatically.

The event can be fired from surrounding web applications via the following snippet (using the default event name of `access-token-loaded`):

```js
const accessToken = "my-access-token";
new CustomEvent("access-token-loaded", {
  detail: {
    accessToken,
  },
});
```

## Styling

The provided WebComponents expose different custom CSS variables that can be used to modify the look of the component by the surrounding application.
If a variable gets not provided, a specific default value will be used automatically.

The following CSS variables are provided:

|                                        | Description                                                                                  | Default                         |
| -------------------------------------- | -------------------------------------------------------------------------------------------- | ------------------------------- |
| `digiwf-webcomponent-color-primary`    | Sets the `background-color` property used for input elements (e.g. buttons, pagination)      | `#FC0`                          |
| `digiwf-webcomponent-color-text`       | Sets the `color` property for all text-based HTML-tags                                       | `#444`                          |
| `digiwf-webcomponent-color-icon`       | Sets the `color` property for all icons                                                      | `#FC0`                          |
| `digiwf-webcomponent-color-background` | Sets the `background-color` property                                                         | `#FFF`                          |
| `digiwf-webcomponent-color-hover`      | Sets the `background-color` property when hovering input elements (e.g. buttons, pagination) | `#FDB813`                       |
| `digiwf-webcomponent-color-separator`  | Sets the `border-color` property of the separating line between list elements                | `#DADADA`                       |
| `digiwf-webcomponent-font-header`      | Sets the shorthand `font` property for the header of the web component                       | `bold 1.3rem Arial, sans-serif` |
| `digiwf-webcomponent-font-title`       | Sets the shorthand `font` property for the title of each element                             | `bold 1.1rem Arial, sans-serif` |
| `digiwf-webcomponent-font-text`        | Sets the shorthand `font` property for text                                                  | `1rem/1.5 Arial, sans-serif`    |
| `digiwf-webcomponent-font-footer`      | Sets the shorthand `font` property for the footer of the web component                       | `1rem Arial, sans-serif`        |
| `digiwf-webcomponent-border-radius`    | Sets the `border-radius` property of the surrounding container and all input elements        | `0px`                           |
| `digiwf-webcomponent-shadow`           | Sets the `shadow` property of the surrounding container                                      | `0px`                           |

**Info:**: Setting the `font` properties expects a value of format `<font-style> <font-variant> <font-weight> <font-size>/<line-height> <font-family>`
Only `font-size` and `font-family` are required. Unset values will use browser defaults.

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

Prerequisites:

- Local keycloak installation running on hostname `keycloak` on port 8080 (otherwise authentication will not work)
- Running DigiWF-APIGateway and DigiWF-Engine services (otherwise no data can be fetched)

1. Checkout
2. run `npm run dev`
3. Open `localhost:8085` or...
4. ...Integrate into your own dev-site alongside your WebComponent:

```html
<!-- Load webcomponent from local dev-server -->
<script
  src="http://localhost:8085/src/digiwf-service-instances-webcomponent.ts"
  type="module"
></script>
<script
  src="http://localhost:8085/dev/retrieveAndDispatchAccessToken.ts"
  type="module"
></script>

<digiwf-service-instances-webcomponent></digiwf-service-instances-webcomponent>
```

After opening the browser you will be required to enter user credentials for the local realm.
Those credentials can be found in the [official documentation](https://digiwf.oss.muenchen.de/documentation/guides/technical-setup/#keycloak-identity-provider)
