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
                <img src="data:image/png;base64,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+9y1a+egft8BWAALYAEsgEUAS3NgxcWVk0ePHml/HC5duiSRkeGOff7009la7uOQIa8BLIAFsAAWwCKABbCcHT16RPvj8PDhw2cPfj527JiW+1i/fl2ABbAAFsACWASwAJazMWNGG3Esxr79tmMJBB3HlStXCuy1DLAAFsACWACLAJYBwIqOjnRcIdJ9JCcnS+/evbTct08++QRgASyABbAAFgEsgPXnzpw5Y8TxWLdunXb7lJWVJfXr1wNYAAtgASyARQALYP25zZs2CSNv4/r161K6dEmABbAAFsACWASwANafGzt2LFLK40hNTX22hATAAlgAC2ABLAJYAOtZiYkJSCmPY9Kk9wv0tQywABbAAlgAiwCWIcBSnT59Gi35ONQje0rGlgBYAAtgASyARQALYOXeokULEZOP4/jx4wX+WgZYAAtgASyARQDLIGB1794NMfk4Fi9eBLAAFsACWACLABbAcl3NmtyH5esYMKAfwAJYAAtgASwCWADLfRkZGajJh6Ge5QiwABbAAlgAiwAWwHLb558vR01ejkOHDhXKaxlgASyABbAAFgEsw4ClViTPzs5GT16MUaNGAiyABbAAFsAigAWwPBceHirXrl1DTx6GenZj7dqJAAtgASyABbAIYAEsz4WEFJUjR44gKA/j9u3bUqpULMACWAALYAEsAlgAy7uWLl2CoDR+nwdYAAtgASyARQDLQGB16fIKgvIwevToDrAAFsACWACLCGD59jFheno6inIxbt26JaGhxQAWwAJYAAtgEQEs39qxYweScjFSUlIK9bUMsAAWwAJYhTbGv/ceqCCAlY/GvfsuknIxZs+eDbAAFsACWACLCGD5nvrLkJH7eOWVTgALYAEsgAWwiACW78XEFEdSuQy1PEN0dCTAAlgAC2ABLCKAlbeSk5MR1V/Gpk2bCv21DLAAFsAysHr16tjiTXD69OmgggBWPuvTpzei+svo1KkjwAJYAAtg+V6LFi/a4k2QbxESwMp/ZcuWkXv37qGq38eTJ0+kTp3aAAtgASyAFbzA2rZtK6gggJXP1L1Gv/zyC7L6fdy9e1ciI8MBFsACWADL93r37mWLN8Jjx45JsWJFgAUBrHx24MB+ZPX7WL58uRavZYAFsACWgY14801bvBFevHhRihePAhYEsPLZqFEjkdXvIy6uLMACWAALYAU3sNSl/DJlSgELAlj5rHTpUvLo0aOgx5X6u0mX1zLAAlgAC2AV6mjTuhWwIIDlh3788cegB5ZO7+sAC2ABLIBVyOdhD7AggOWH1L1HwT6mTJkMsAAWwAJYAOvpaNiwAbgggJXPBg4cEPTAqle3DsACWAALYOW94cOG2epNcfnyz8AFAax8lphYM6hxlZaWptVrGWABLIBlYK1atbTVG2NWVpZ0794VYBDAykdqyRP1zdxgHbNmzQJYAAtgASwWGv3ruHLlijRv3hRkACyAlY9mz54VtMDq2rUzwAJYAAtg5a9atez7UcDbb78lcXHl/Hq8QkKKOrZZo0Y1EAOwbA2s2rVrOR4VE4yjWrV4gAWwABbAyn92Ho8fP5Z9+76VYcPekF69enpVv359ZdHChc9SK1sfPXpUbty48Wy7Oq2RQwArEFWoECd379wJOlydP39eu9cywAJYAMvQrl69KgzfBsACWHYHVlhYiJw9ezbofrcVZgAWwAJYAMsvnWRRQYAFsABWLi1cuCCofq8fPnyo5SO3ABbAAliGtm3bVsQEsAAWwHquxo0bBtXv9YULFyQ8PBRgASyABbD808yZHyMmgAWwAFauXbsWPLcQbN26VcvXMsACWADL0AYPHoiYABbAAlgurnBvC5rf6xEj3gRYAAtgASz/1bp1K8QEsAAWwMo19Uy+YBjqG8exJWIAFsACWADLf5UrVwYxASyABbByrWPHDkHxO52amqrtaxlgASyAZXA//PADagJYAAtgPZf6Vt29e/ds/zutrtQBLIAFsACW3xszZjRqAlgAC2Dl2pYtm23/O62u1AEsgAWwAJbfK126pGRmZiIngAWwANZzvdqnj+1/p2NiogEWwAJYAMv/qbVfTp86hZwAFsACWM9Vr14dx03gdh0HDhzQ+rUMsAAWwDI8U3+JARYBrMBWsmQJycjIsOXvcnZ2tvTo0Z33ZoAFsABWYLt79y56AlgAC2A91/79+235u5yR8ZvExZUFWAALYAGswLZk8WL0BLAAFsB6rtdeG2zL3+W0tDSJjAwHWAALYAGswFa/Xl1b32sBsAAWwMp7dlyuISkpids3ABbAAlgF09y5cxAUwAJYAOu5jh8/brvf5c6dOwEsgAWwAFbBVKpUrFy/fh1FASyABbD+1OrVq2z1e3zjxg2+gASwABbAKth69eop9+/fR1IAC2ABrGcNHz7MVr/HW7f+HWABLIAFsAq+BQvmIymABbAA1rPUt+2ysrJs83s8duzbAAtgASyAVfBFRUUExSMyABbAAljed+zYMdv8Hrdp0wpgASyABbAK636skvL9998jKoAFsACWo48//tgWv8O3bt2S8LBQgAWwABbAKtz27fsWVQEsgAWwpGPH9rb4HV62dClP2QBYAAtgFX5hYSGyZMkSZAWwAFaQA6tq1Xjjf3/V43EaNWoIsAAWwAJYehQRESY9e/aQmzdvAiyABbCCFFghIUXl6tWrRv/+/vbbb1KpUgWABbAAFsDSq4SEGrJ582ZbfZsIYAEsgOV98ww9tk/HuXPnJDw8FGABLIAFsPSsSuVKcubMGcfldoBFACt4gFU+rpzRv79z58416rUMsAAWwArC1Ldw2rZtLevWrZNHjx4BLNKmoUOHyLatW42rSeNGRhxftaq7icdXVaFCnFGv5X79+hp5nOvUqQ2wCjN1Aho2bGB0NWpU4y+0nOLjK8vkyZMkNTXVcY8DwCIiIoBF5OcGDx4ke/bslvT0dMdjd548eWIsqB4/fiyZmZly+/YtOXXqlMyePYtzTEQEsIgKr5iYaKlevao0atRAJk6cIGvXrJGff/5ZW0ypm/f/+c9/yt+/+UamTZsqzZo1lXr16kilShUlNjaGc0pEBLCI9P66d82aCdKhQ3vp16+fjB49Sj76aIbMmfOpbNq0yfGYnu9SU+XokSOOTp8+LVeuXMlT6mZ8tY0jOakHvKptz5z5sSP15/bv3086duwgCQnVOTdERASwiIiIiAAWEREREcAiIiIiAlhEREREBLCIiIiIABYRERERwCIiIiIigEVEREQEsIiIiIgAFhEREREBLCIiIiKARURERASwiIiIiAAWB4KIiIgIYBEREREBLCIiIiKARUREREQAi4iIiAhgEREREQEsIiIiIgJYRERERACLiIiICGAREREREcAiIiIiAlhEREREAIuIiIgIYBERERERwCIiIiICWEREREQAi4iIiIgAFhERERHAIiIiIrJZ/x+z8L9ZWpYQAQAAAABJRU5ErkJggg==" width="200" alt="itm_logo" border="0">
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
