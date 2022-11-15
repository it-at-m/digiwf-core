# Documentation

## Usage

The digiwf-email-integration is a generic integration artifact to send email from processes.

To send an email through the event bus you have to send
a *[Mail](https://github.com/it-at-m/digiwf-email-integration/tree/dev/digiwf-email-integration/src/main/java/io/muenchendigital/digiwf/email/integration/domain/model/Mail.java)*
event with the TYPE-Header `sendMailFromEventBus` to the event bus topic specified
in `io.muenchendigital.digiwf.email.topic`.

````json
{
  "receivers": "receivers@example.com",
  "receiversCc": "receivers-on-cc@example.com",
  "receiversBcc": "receivers-on-bcc@example.com",
  "subject": "My important email",
  "body": "Some text I want to send",
  "replyTo": "replyto@example.com",
  "attachments": [
    {
      "url": "http://localhost:9000/s3-bucket/some/path/to/file/image.png",
      "path": "path/to/file/in/s3",
      "action": "GET"
    }
  ]
}
````

### Send Mail with file attachments

You can attach files from a s3 storage to the emails you are sending.
Therefore, you have to obtain presigned urls from the s3 integration artifact and pass them in the attachment section
of the *Mail* event to the digiwf-email-integration.
The email integration will download the files and attach them to the email before sending it.

**Note**: The digiwf-email-integration only supports presigned urls created with the **GET** action.
All other file action will not work and result in an error.

### Element Template

To speed up process development you can use the element template [sendMail.json](sendMail.json) to define a call
activity
that uses this integration.

Checkout [https://digiwf.muenchendigital.io/resources/documentation/concept/filehandling/](https://digiwf.muenchendigital.io/resources/documentation/concept/filehandling/)
for more information about the topic file handling in digiwf.
