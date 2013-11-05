(function(){// Our collection of tiles
Tiles = new Meteor.Collection("tiles");

// Publish complete set of tiles to all clients
Meteor.publish("tiles", function () {
	return Tiles.find({});
});

})();
