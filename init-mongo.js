db = db.getSiblingDB('SalePointCredentialDB');
db.createCollection('acreditacionesV2');
db.acreditacionesV2.insertMany([
    {
        _id: "1",
        salePointName: "CABA",
        amount: 100.50,
        receptionDate: new Date("2025-03-23T10:00:00Z"),
		_class: 'com.challenge.v2.commons.model.SalePointCredential'
    },
    {
        _id: "2",
        salePointName: "GBA_1",
        amount: 250.75,
        receptionDate: new Date("2025-03-24T12:30:00Z"),
		_class: 'com.challenge.v2.commons.model.SalePointCredential'
    }
]);