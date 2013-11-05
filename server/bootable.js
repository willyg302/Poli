// If the database is empty on server start, create some sample data
Meteor.startup(function () {
	if (Tiles.find().count() === 0) {
		var data = JSON.parse(Assets.getText("default-data.json"));
		for (var i = 0; i < data.length; i++) {
			Tiles.insert(data[i]);
		}
	}
});