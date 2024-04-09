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
                <img src="data:image/png;base64,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" alt="itm_logo" border="0">
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
