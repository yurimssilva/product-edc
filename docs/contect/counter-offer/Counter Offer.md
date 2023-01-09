# Non-Cataloged Contract Offer

## Terminology

### Contract Agreement

If two connectors agree to the terms of a contract offer, they conclude a contract
agreement. A contract agreement is always preceded by a successful contract negotiation.

This document will assume that a contract agreement is made between exactly two connectors, where one is
the **Promisee** and one is the **Promisor**. The case, where a contract agreement may contain more than two connectors
and more
than one **Promisee** and one **Promisor**, is ignored for now.

### Promisee

The **Promisee** of a Contract Agreement is the party to whom a promise was made to. The other party/connector of the
Contract Agreement is called **Promisor**.

### Promisor

The **Promisor** of a Contract Agreement is the party to who makes a promise to a **Promisee**.

### Data Provider

The **Data Provider** sends data to the **Data Consumer**. It may be the **Promisee** or **Promisor** of a Contract
Agreement.

### Data Consumer

The **Data Consumer** gets data from the **Data Provider**. It may be the **Promisee** or **Promisor** of a Contract
Agreement.

### Contract Offer

There are two different situations in which a **Contract Offer** occurs.

1. A **Contract Offer** may be part of the **Contract Offer Catalog**
2. A **Contract Offer** may be exchanged between two connectors during Contract Negotiation.

#### Contract Offer Categories

There exists four different categories of contract offers. A contract offer may belong to none or multiple of the
categories described below.

1. Initial Contract offer
2. Counter Contract offer
3. Cataloged Contract offer
4. Non-cataloged Contract offer

##### 1. Initial Contract Offer

The first contract offer of each contract negotiation is called **Initial Contract Offer**. It may be sent out by the *
*Data
Consumer** or **Data Provider**.

##### 2. Counter Contract Offers

During a Contract Negotiation, if one connectors does not agree to the offer made, it can send another offer back to the
other connector. This newly made offer is called counter offer.

> Please note: At the time of writing the EDC does not support counter offers.

##### 3. Cataloged Contract Offer

A **Cataloged Contract Offer** has identical terms and conditions as a Contact Offer from the **Contract Offer Catalog
**.

##### 4. Non-Cataloged Contract Offer

A **Non-Cataloged Contract Offer** has different terms and conditions than all Contract Offer from the **Contract Offer
Catalog**.

### Contract Offer Catalog

A connector may provide multiple incomplete **Contract Offers** to other connectors. For this document it is to assume,
that the catalog providing connector is always the **Data Provider**, and the **Promisee** is left empty in the *
*Catalog Contract Offers**.

### Contract Negotiation

The steps two Connectors take, to conclude a Contract Agreement, are called Contract Negotiation. A Contract Negotiation
consists of a Contract Offer exchange, until both connectors agree to its conditions.

A contract negotiation is initiated by sending out a contract offer from one connector to the other connector. It is
always either
a **Cataloged Contract Offer** or a **Non-Cataloged Contract Offer**. The first Contract offer is also
called **Initial Contract Offer**. If one party sends back a different Contract Offer during the Negotiation, it is
called **Counter Contract Offer**. The first Contract Offer can never be a **Counter Contract Offer**.

### New Role Examples

#### (Old) 1- Alice offers data in form of a catalog

This is the easiest example. Alice has a **Contract Offer Catalog** and Bob can pick an offer, and if a
contract concludes, consume the data behind it.

The roles are as follows:

- Contract offer is cataloged
- Alice is the **Data Provider**
- Alice is the **Promisor** (Alice promises to provide Data)
- Bob is the **Data Consumer**
- Bob is the **Promisee**

#### (New) 2. Alice requires Bob to alert her in case of Quality Alerts

Maybe Alice has legal governance requirements to inform Bob about a special kind of quality alerts.
As it is very important for Alice to be legal complient, she sends out a **Non-Cataloged Contract Offer** to Bob to initiate the Contract Negotiation.

The roles are as follows:

- Contract offer is non-cataloged
- Alice is the **Data Provider**
- Alice is the **Promisee**
- Bob is the **Data Consumer**
- Bob is the **Promisor** (Bob promises to consume whatever Alice sends him)

### (New) 3. Alice promises Bob to alert him in case of Quality Alerts

Bob wants to receive Quality Alerts, but it is not possible to provide a general "one-fits-all" Quality Alert contract
for his partners. So Alice will have to make anew Contract wit Bob, so that she can inform him.

The roles are as follows:

- Contract offer is non-cataloged
- Alice is the **Data Provider**
- Alice is the **Promisor** (Alice promises to send Bob the data, if the contract concludes)
- Bob is the **Data Consumer**
- Bob is the **Promisee**

### Summary

With only Alice as data provider the roles are split as follows. Adding Bob as Data Provider, too, would be very similar
as example 2 and 3. So Bob as Data Provider was left out.

| Example | Contract Offer | Promisor | Promisee | Data Provider | Data Consumer |
|:--------|:---------------|:---------|:---------|:--------------|:--------------|
| 1       | Cataloged      | Alice    | Bob      | Alice         | Bob           |
| 2       | Non-Cataloged  | Bob      | Alice    | Alice         | Bob           |
| 3       | Non-Cataloged  | Alice    | Bob      | Alice         | Bob           |

## Conceptual

## How identity a connector?

At the time of writing a connector is identified by its IDS Callback URL (e.g. https://example.com/api/v1/ids/data).
This identification is only stored in the Contract Negotiation, but never in the Contract Offer or -Agreement itself.

For the initial implementation we probably can assume that the IDS Callback URL is good enough to identify a **Data
Consumer**, **Data Provider**, **Promisee** and **Promisor**. Not because it is sufficient, **only** because it is **not
worse** than it is now.

This leads to the next two questions.

- Why is the connector URL inadequate to identity a Data Consumer, -Provider, Promisee or Promisor?
- What should be used instead of the connector URL?

### Why is the connector URL inadequate to identity a Data Consumer, -Provider, Promisee or Promisor?

One party should identify another party using a non-forgeable, trusted piece of information, another connector used to
prove its identity.

**Is the connector URL a piece of information like this?** The answer is always no. The connector URL can be configured
by any person, that runs a connector. This is nether trusted, or non-forgeable or part of any identity. So a person
would be able to register a domain, run a connector behind this domain,
negotiation a contract with a valid CX/DAPS identity, transfer some data, and unregisters the domain. So with which
party was the contract concluded? In case the data becomes public, who breached the contract? Impossible to know.

### What should be used instead of the connector URL?

The information to identify another party must come from a trusted source of information, and this is rarely the
connector itself.

**DAPS** Assuming two connectors build their trust using a DAPS instance, the piece of information must be taken from
the
DAPS token. At the time of writing the only use able piece of information inside the DAPS token is the **BPN**.

**SSI** Assuming two connector build their trust over SSI, the piece of information must come from a trusted Verifiable
Credential Issuer. As there is still a lot to refine within the current DAPS implementation, it is not possible to say
what this piece of information must be. As the BPN is probably possible, too, the DID itself would probably be better,
as long as Verifiable Credentials (that belong to a contract) are archived for a reasonable time.

But still, let's ignore this problem within this document.

## Implementation

With the terminology and the examples it should be clear what the connector must support. This chapter will try to find
all pieces, that would have to change in the code and suggests how to change them.

### Data Model

#### Contract Offer

At the time of writing the Contract Offer only has a single property for each connector to identify them. The `provider`
URI and the `consumer` URI. This works for the current implementation, because the `provider` is the **Data Provider**
and **Promisee** at the same time. And the `consumer` is the **Data Consumer** and **Promisor** at the same time.

For **Non-Cataloged Contract Offers** it will be important to have a distinction between **Data Consumer**, **Data
Provider**, **Promisee** and **Promisor**. This will make it mandatory to add two additional properties to the Contract
Offer.

Additionally, the `consumer` URI is **always** 'urn:connector:consumer', and the `provider` is **always** 'urn:
connector:
provider'. So at the time of writing there is no real information stored, in these properties.

#### Contract Agreement

The Contract Agreement has only two properties to identity the participating connectors. The `providerAgentId`
and `consumerAgentId` property. This leads to the same problem described in chapter 'Contract Offer' above.

#### Contract Negotiation

### Contract Validation

The Contract Validation must be able to _differentiate_ between **Cataloged-** and **Non-Cataloged Contract Offers**.

**In case of Cataloged Contract Offers** the Validation must ensure **Data Provider**, **-Consumer**, **Promisee** and *
*Promisor** are as expected as in **Cataloged Contract Offers**.

**In case of Non-Cataloged Contract Offers** there should be an option to **configure** how the connector handles them.
The default option should be the current behavior, to reject all Non-Cataloged Contract Offers. Using a second option it
should be possible to add a manual behavior too, that makes it possible to manage these Non-Cataloged Contract Offers,
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
    - Data Provider
    - Data Consumer
    - Promisee
    - Promisor
- `offerId` must become optional

**Bonus** Remove the `connectorId` property, as it's not used for anything anyway.

> Please note that this leads again to breaking changes in the (Data) Management API.

### Accept Contract Negotiation

A new API Call to accept pending Contract Offers must be added.

### (Optional) Usability

Using the proposed Model changes it would still be transparent whether a Contract Offer is Cataloged or Non-Cataloged.
Hence, the user cannot now whether its mandatory to manually approve/reject an Offer, or not.

## Non-Implementation Tasks

Besides the implementation issues the following tasks must be addressed

- Business Test for Non-Cataloged Contract Offer
- A lot of refactoring due to changed model classes and business logic
- A lot of documentation, as nothing is documented regarding Contract Negotiation