<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"> <!-- utf-8 works for most cases -->
    <meta name="viewport" content="width=device-width"> <!-- Forcing initial-scale shouldn't be necessary -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge"> <!-- Use the latest (edge) version of IE rendering engine -->
    <title>DigiWF Benachrichtigung</title> <!-- The title tag shows in email notifications, like Android 4.4. -->

    <!-- Web Font / @font-face : BEGIN -->
    <!-- NOTE: If web fonts are not required, lines 9 - 26 can be safely removed. -->

    <!-- Desktop Outlook chokes on web font references and defaults to Times New Roman, so we force a safe fallback font. -->
    <!--[if mso]>
    <style>
        * {
            font-family: sans-serif !important;
        }
    </style>
    <![endif]-->

    <!-- All other clients get the webfont reference; some will render the font and others will silently fail to the fallbacks. More on that here: http://stylecampaign.com/blog/2015/02/webfont-support-in-email/ -->
    <!--[if !mso]><!-->
    <!-- insert web font reference, eg: <link href='https://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'> -->
    <!--<![endif]-->

    <!-- Web Font / @font-face : END -->

    <!-- CSS Reset -->
    <style type="text/css">

        /* What it does: Remove spaces around the email design added by some email clients. */
        /* Beware: It can remove the padding / margin and add a background color to the compose a reply window. */
        html,
        body {
            margin: 0 auto !important;
            padding: 0 !important;
            height: 100% !important;
            width: 100% !important;
        }

        /* What it does: Stops email clients resizing small text. */
        * {
            -ms-text-size-adjust: 100%;
            -webkit-text-size-adjust: 100%;
        }

        /* What is does: Centers email on Android 4.4 */
        div[style*="margin: 16px 0"] {
            margin:0 !important;
        }

        /* What it does: Stops Outlook from adding extra spacing to tables. */
        table,
        td {
            mso-table-lspace: 0pt !important;
            mso-table-rspace: 0pt !important;
        }

        /* What it does: Fixes webkit padding issue. Fix for Yahoo mail table alignment bug. Applies table-layout to the first 2 tables then removes for anything nested deeper. */
        table {
            border-spacing: 0 !important;
            border-collapse: collapse !important;
            table-layout: fixed !important;
            Margin: 0 auto !important;
        }
        table table table {
            table-layout: auto;
        }

        /* What it does: Uses a better rendering method when resizing images in IE. */
        img {
            -ms-interpolation-mode:bicubic;
        }

        /* What it does: A work-around for iOS meddling in triggered links. */
        .mobile-link--footer a,
        a[x-apple-data-detectors] {
            color:inherit !important;
            text-decoration: underline !important;
        }

    </style>

    <!-- Progressive Enhancements -->
    <style>

        /* What it does: Hover styles for buttons */
        .button-td,
        .button-a {
            transition: all 100ms ease-in;
        }
        .button-td:hover,
        .button-a:hover {
            background: #3f51b5 !important;
            border-color: #3f51b5 !important;
        }

        /* Media Queries */
        @media screen and (max-width: 600px) {

            .email-container {
                width: 100% !important;
                margin: auto !important;
            }

            /* What it does: Forces elements to resize to the full width of their container. Useful for resizing images beyond their max-width. */
            .fluid,
            .fluid-centered {
                max-width: 100% !important;
                height: auto !important;
                Margin-left: auto !important;
                Margin-right: auto !important;
            }
            /* And center justify these ones. */
            .fluid-centered {
                Margin-left: auto !important;
                Margin-right: auto !important;
            }

            /* What it does: Forces table cells into full-width rows. */
            .stack-column,
            .stack-column-center {
                display: block !important;
                width: 100% !important;
                max-width: 100% !important;
                direction: ltr !important;
            }
            /* And center justify these ones. */
            .stack-column-center {
                text-align: center !important;
            }

            /* What it does: Generic utility class for centering. Useful for images, buttons, and nested tables. */
            .center-on-narrow {
                text-align: center !important;
                display: block !important;
                Margin-left: auto !important;
                Margin-right: auto !important;
                float: none !important;
            }
            table.center-on-narrow {
                display: inline-block !important;
            }

        }

    </style>

</head>
<body bgcolor="white" width="100%" style="Margin: 0;">
<center style="width: 100%; background: white;">

    <!-- Email Header : BEGIN -->
    <table cellspacing="0" cellpadding="0" border="0" align="center" width="600" style="margin: auto;" class="email-container">
        <tr>
            <td style="padding: 20px 0; text-align: center">
                <svg id="a" height="100" aria-label="it@m logo" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 118.98 134.44"><defs><style>.b{fill:#fff;}.c{fill:#fc0;}.d{fill:#1d1d1b;}</style></defs><polygon class="d" points="0 33.61 0 100.83 59.49 134.44 118.98 100.83 118.98 33.61 59.49 0 0 33.61 0 33.61"/><path class="b" d="M10.56,57.74v-6.77h9.7v6.77H10.56Zm.05,29.72v-25.37h9.6v25.37H10.61Z"/><path class="b" d="M29.41,62.08v-3.84l9.6-3.64v7.48h5.56v6.06h-5.56v10.06c0,.66-.4,3.94,1.82,3.94,1.26,0,2.58,0,3.69-.15v5.71c-2.17,.4-3.74,.51-5.16,.51-6.52,0-10.46-.91-9.96-8.69v-11.37h-4.95v-6.06h4.95Z"/><polygon class="b" points="64.85 87.38 64.85 50.88 80.87 50.88 86.84 75.6 86.94 75.6 92.7 50.88 108.42 50.88 108.42 87.38 99.57 87.38 99.57 56.85 99.47 56.75 90.93 87.38 81.83 87.38 73.8 56.49 73.7 56.6 73.7 87.38 64.85 87.38 64.85 87.38"/><polygon class="c" points="42.17 43.19 42.17 58.79 48.48 58.79 48.59 68.19 58.11 58.79 61.62 58.79 61.62 43.19 42.17 43.19 42.17 43.19"/><path class="d" d="M55.34,48.22l-1.13,4.06c-.07,.46,.01,.73,.51,.82,1.47,.27,2.15-1.95,2.15-3.07,0-2.51-2.33-3.75-4.57-3.64-3.04,.11-5,2.57-4.91,5.23,.09,2.53,1.98,3.91,4.48,4.02,1.06,.04,2.03-.19,2.89-.55l.39,1.2c-.99,.51-2.23,.7-3.32,.7-3.21,0-5.98-2.08-5.98-5.43-.04-3.46,3.11-6.63,7.17-6.51,2.94,.09,5.28,2.18,5.2,5.3-.05,1.84-1.47,4.09-3.52,3.94-.75-.05-1.17-.26-1.55-.9-.91,.74-1.4,.95-2,.95-1.34,0-2.23-.85-2.18-2.18,.07-1.73,1.34-3.97,3.28-4.09,.65,0,1.44,.27,1.67,.94h.03l.19-.79h1.21Zm-3.15,.89c-.93,0-1.68,1.67-1.75,2.54-.08,.97,.19,1.32,.78,1.45,1.2,.27,2.04-1.3,2.21-2.18,.22-1.16-.46-1.9-1.24-1.82h0Z"/></svg>
            </td>
        </tr>
    </table>
    <!-- Email Header : END -->

    <!-- Email Body : BEGIN -->
    <table cellspacing="0" cellpadding="0" border="0" align="center" bgcolor="#ffffff" width="600" style="margin: auto;" class="email-container">

        <!-- 1 Column Text : BEGIN -->
        <tr>
            <td style="padding: 40px; font-family: sans-serif; font-size: 15px; mso-height-rule: exactly; line-height: 20px; color: #555555;">
                ${mail.text}
                <#if mail.buttonLink?has_content && mail.buttonText?has_content>
                    <br><br><br>
                    <!-- Button : Begin -->
                    <table cellspacing="0" cellpadding="0" border="0" align="center" style="Margin: auto">
                        <tr>
                            <td style="border-radius: 3px; background: #3f51b5; text-align: center;" class="button-td">
                                <a href="${mail.buttonLink}" style="background: #3f51b5; border: 15px solid #3f51b5; font-family: sans-serif; font-size: 13px; line-height: 1.1; text-align: center; text-decoration: none; display: block; border-radius: 3px; font-weight: bold;" class="button-a">
                                    &nbsp;&nbsp;&nbsp;&nbsp;<span style="color:#ffffff">${mail.buttonText}</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                </a>
                            </td>
                        </tr>
                    </table>
                    <!-- Button : END -->
                </#if>
                <br /><br />
                ${mail.bottomBody}
            </td>
        </tr>
        <!-- 1 Column Text : BEGIN -->

    </table>
    <!-- Email Body : END -->

    <!-- Email Footer : BEGIN -->
    <table cellspacing="0" cellpadding="0" border="0" align="center" width="600" style="margin: auto;" class="email-container">
        <tr>
            <td style="padding: 30px 10px;width: 100%;font-size: 12px; font-family: sans-serif; mso-height-rule: exactly; line-height:18px; text-align: center; color: #888888;">
                ${footer}
            </td>
        </tr>
    </table>
    <!-- Email Footer : END -->

</center>
</body>
</html>
