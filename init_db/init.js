
db.complain.createIndex({"locale.point": "2dsphere"})
db.complain.insert([{"uuid":"5cb2dea4a7b11b00018f4d50","title":"cobrança indevida","description":"bla bla bla","locale":{"point":[-25.477564, -49.29314],"city":"Curitiba","state":"PR","country":"BR"},"company":{"name":"renner"}}]);