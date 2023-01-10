# Non-Cataloged Contract Offer

## Terminology

### Contract Agreement

If two parties agree to the terms of a contract offer, they conclude a **Contract
Agreement**. A Contract Agreement is always preceded by a successful Contract Negotiation.

> This document will assume that a Contract Agreement is always made between exactly two parties, where one
> is the **Data Provider** and the other one is the **Data Consumer**. The case, where a Contract Agreement may contain
> more than two parties, is ignored for now.

### Data Provider

The **Data Provider** sends data to the **Data Consumer**. There can only exist a single **Data Provider** per Contract
Agreement.

### Data Consumer

The **Data Consumer** gets data from the **Data Provider**. There can only exist a single **Data Consumer** per Contract
Agreement.

### Contract Offer

There are two different situations in which a **Contract Offer** occurs:

1. A **Contract Offer** may be part of the **Contract Offer Catalog**
2. A **Contract Offer** may be exchanged between two connectors during Contract Negotiation.

#### Contract Offer Categories

There exists four different categories of contract offers. A contract offer may belong to none or multiple of the
categories described below.

The **Initial Contract Offer** is the first contract offer of each contract negotiation. It may be sent out by the
**Data Consumer** or **Data Provider**.

The **Counter Contract Offer**, is send out _during_ a Contract Negotiation. It must be different than the previous
exchanged Contract Offer. The TXDC does not support Counter Offers.

The **Cataloged Contract Offer** has identical terms and conditions as any Contact Offer from the **Contract Offer
Catalog**.

The **Non-Cataloged Contract Offer** has different terms and conditions than all
Contract Offer from the **Contract Offer Catalog**.

### Contract Offer Catalog

A connector may provide multiple **Contract Offers** to other connectors as part of a **Contract Offer Catalog**.

### Contract Negotiation

The steps two Connectors take, to conclude a Contract Agreement, are called **Contract Negotiation**. A Contract
Negotiation
consists of a Contract Offer exchange, until both connectors agree to its conditions.

A Contract Negotiation is initiated by sending out a Contract Offer from one connector to the other connector. It is
always either a **Cataloged Contract Offer** or a **Non-Cataloged Contract Offer**. The first Contract Offer is also
called **Initial Contract Offer**. If one party sends back a different Contract Offer during the Negotiation, it is
called **Counter Contract Offer**. The first Contract Offer can never be a **Counter Contract Offer**.

### Examples

#### (Old) 1. Alice offers data as part of a catalog

This is the easiest example. Alice has a **Contract Offer Catalog**. Bob can pick an offer, and if a
contract concludes, consume the data behind it.

The roles are as follows:

- Contract Offer is cataloged
- Alice is the **Data Provider**
- Bob is the **Data Consumer**
- Initial Contract Offer send out by Bob

#### (New) 2. Alice requires Bob to alert her in case of Quality Alerts

Maybe Alice has legal governance requirements to inform Bob about a special kind of quality alerts.
As it is very important for Alice to be legal compliant, she sends out a **Non-Cataloged Contract Offer** to Bob to
initiate the Contract Negotiation.

The roles are as follows:

- Contract Offer is non-cataloged
- Alice is the **Data Provider**
- Bob is the **Data Consumer**
- Initial Contract Offer send out by Alice

#### (New) 3. Bob wants to be informed about Quality Alerts of his suppliers

Bob wants to be alert by suppliers in case of Quality Alerts. But not all suppliers have a Contract Offer for Quality
Alerts, Bob can accept. So Bob sends out a different Contract Offer to Alice.

The roles are as follows:

- Contract Offer is non-cataloged
- Alice is the **Data Provider**
- Bob is the **Data Consumer**
- Initial Contract Offer send out by Bob

> Please note: This is not a Counter Offer, as defined in the Terminology chapter.

### Summary

In these examples the roles are split as follows.

| Example | Contract Offer | Data Provider | Data Consumer | Initial Contract Offer Sender |
|:--------|:---------------|:--------------|:--------------|:------------------------------|
| 1       | Cataloged      | Alice         | Bob           | Bob                           |
| 2       | Non-Cataloged  | Alice         | Bob           | Alice                         |
| 3       | Non-Cataloged  | Alice         | Bob           | Bob                           |

> Please note: The fourth case (Cataloged, with Alice as Data Consumer) is not possible within the current EDC. As this
> case plays no role for Non-Cataloged Contract Offers, it will be skipped. The current uses of this example in
> the EDC (also called Data Push Scenarios) are technically possible from a transfer point of view, but cannot be
> reflected in the Contract between the two connectors.

## Conceptual

## How identity a connector?

At the time of writing a connector is identified by its IDS Callback URL (e.g. https://example.com/api/v1/ids/data).
This identification is only stored in the Contract Negotiation, but never in the Contract Offer or -Agreement itself (
where a dummy placeholder value is used).

For the initial implementation we probably can assume that the IDS Callback URL is good enough to identify a **Data
Consumer** and **Data Provider**. Not because it is sufficient, **only** because it will **not get worse** than it is
now.

This leads to the next two questions.

- Why is the connector URL inadequate to identity a Data Consumer or -Provider?
- What should be used instead of the connector URL?

### Why is the connector URL inadequate to identity a Data Consumer or -Provider?

One party should identify another party using a non-forgeable, trusted piece of information, another connector uses to
prove its identity.

**Is the connector URL a piece of information like this?** The answer is always no. The connector URL can be configured
by any person, that runs a connector. This is nether a trusted, nor a non-forgeable, nor a part of any identity token.
In short: A party could get URL, run a connector behind it, negotiation a contract with a valid CX/DAPS identity,
transfer some data, and unregisters at the URL. So with which party was the contract concluded? In case the data becomes
public, who breached the contract? Impossible to know.

### What should be used instead of the connector URL?

The information to identify another party must come from a trusted source of information, and this is rarely the
connector itself.

**DAPS** Assuming two connectors build their trust using a DAPS instance, the piece of information must be taken from
the
DAPS token. At the time of writing the only use able piece of information inside the DAPS token is the **BPN**.

**SSI** Assuming two connector build their trust over SSI, the piece of information must come from a trusted Verifiable
Credential Issuer. As there is still a lot to refine within the current SSI implementation, it is not possible to say
what this piece of information must be. As the BPN is probably possible, too, the DID itself would probably be better,
as long as Verifiable Credentials (that belong to a contract) are archived for a reasonable time.

Still, let's ignore this connector identity issue within this document and solve it another time.

## Implementation

With the terminology and the examples it should be clear what the connector must support. This chapter will try to find
all pieces, that would have to change in the code and suggests how to change them.

### Contract Offer

At the time of writing the Contract Offer only has a single property for each connector to identify them. The `provider`
URI and the `consumer` URI. But they are always set to some hard coded dummy value.

For **Non-Cataloged Contract Offers** it will be important to have a clear distinction between **Data Consumer** and *
*Data
Provider**.

### Contract Agreement

The Contract Agreement has only two properties to identity the participating connectors. The `providerAgentId`
and `consumerAgentId` property. This leads to the same problem described in chapter 'Contract Offer' above.

### Contract Negotiation

The Contract Negotiation must be able to _differentiate_ between **Cataloged-** and **Non-Cataloged Contract Offers**.

**In case of Cataloged Contract Offers** the Validation must ensure **Data Provider** and **-Consumer** are as expected
as in **Cataloged Contract Offers**.

**In case of Non-Cataloged Contract Offers** there should be an option to **configure** how the connector handles them.
The default option should be the current behavior, to reject all Non-Cataloged Contract Offers. Using a second option it
should be possible to add a manual behavior, too, that makes it possible to manage these Non-Cataloged Contract Offers
using the (Data) Management API.

### (Data) Management API

#### Initiate Contract Negotiation Call

At the time of writing the payload of the Contract Negotiation Call looks as follows:

```json
{
  "connectorId": "foo",
  "connectorAddress": "{{PROVIDER_IDS_URL}}/api/v1/ids/data",
  "offer": {
    "offerId": "{{CONTRACT_DEFINITION_ID}}:foo",
    "assetId": "{{ASSET_ID}}",
    "policy": {
      "prohibitions": [],
      "obligations": [],
      "permissions": [
        {
          "edctype": "dataspaceconnector:permission",
          "action": {
            "type": "USE"
          },
          "target": "{{ASSET_ID}}",
          "constraints": []
        }
      ]
    }
  }
}
```

The following changes would become necessary

- New properties for
    - Data Provider (mandatory)
    - Data Consumer (mandatory)
- `offerId` must become optional (or can be removed)
- (Bonus) Remove the `connectorId` property, as it's not used for anything anyway

> Please note that this leads again to breaking changes in the (Data) Management API.

#### Accept Contract Negotiation

A new API Call to accept pending Contract Offers must be added.

##### Addition Improvement for Usability

Using the proposed Model changes it would still be transparent whether a Contract Offer is Cataloged or Non-Cataloged.
Hence, the user cannot tell without checking the catalog, whether its mandatory to manually approve/reject a Contract
Offer, or not.

It would help the user, if he could use a filter in the (Data) Management API for Non-Cataloged Contract Offers.
