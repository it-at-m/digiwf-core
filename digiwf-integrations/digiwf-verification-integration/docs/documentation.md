## Documentation


I can request a verification link from within a business process. 
This verification link has a certain validity, if "Expiry time" has been set. After the expiration of this period, the link can no longer be called. 
This means that clicking on the link does not send a message to a waiting process instance and the user is informed that the validity of the link has already expired.
If the token passed by the link cannot be assigned (via DB lookup), then a generic error message is issued to the user.
The verification link is constructed in such a way that no user data (e-mail, correlation key, etc.) is given to the outside. A random and unique token is generated here.
If the user clicks on the link within the defined expiration period, 
* the message name is sent to the (hopefully) waiting process with the given process instance id.
* feedback is given to the user whether the transaction was successful (the process was activated) or not.
* it cannot be clicked a second time with a positive result.

The following data is needed to get the verification link:

* Process instance id: the id uniquely identifying the process instance.
* Message name: The key that can be uniquely assigned to a message catch event in an active process instance.
* Expiry time (optional): If this parameter is not set, the link is valid "infinitely". If this parameter is set, it is validated on verification.
* Subject (optional): Additional information such as e-mail or similar. In a scenario with a long context (for example, a user account), this parameter could prevent a user from having to verify n times.

In the scenario described above, it does not take into account that the process instance is no longer present at the time of verification (although it still happens in the required time period). The user would then still receive positive feedback because the correlation key was sent to the - supposedly - waiting instance.
<div>
    <img src="https://github.com/it-at-m/digiwf-verification-integration/blob/dev/images/flowchart.png" alt="Architecture"/>
</div>

