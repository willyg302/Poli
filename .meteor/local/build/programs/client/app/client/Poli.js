(function(){// Client-side JavaScript, bundled and sent to client

// Define Minimongo collections to match server/publish.js
Tiles = new Meteor.Collection("tiles");

var tilesHandle = Meteor.subscribe("tiles");

/* Templating */

Template.fbtile.timestamp = Template.twtile.timestamp = function () {
	return moment(this.created).format("h:mm A - d MMM YYYY");
};

Template.page.icon = function () {
	return 'home';
};

Template.page.title = function () {
	return 'Home';
};

Template.page.tiles = function () {
	return Tiles.find({}).fetch();
};

Template.tile.color = function (type) {
	switch (type) {
		case 'facebook': return '3b5998';
		case 'youtube': return 'ff0202';
		case 'twitter': return '1dcaff';
		default: return 'dddddd';
	}
}

/* Events */

Template.tile.events({
	'click .bookmark': function (event) {
		Tiles.update(this._id, {$set: {bookmarked: !this.bookmarked}});
	}
});

// Set up Isotope once the page template has been rendered
Template.page.rendered = function () {
	var $container = $('#container');
	$container.imagesLoaded(function () {
		$container.isotope({
			itemSelector: '.tile'
		});
		var $optionSets = $('.option-set'),
		$optionLinks = $optionSets.find('a');
		$optionLinks.click(function () {
			var $this = $(this);
			// Don't proceed if already selected
			if ($this.hasClass('active')) {
				return false;
			}
			var $optionSet = $this.parents('.option-set');
			$optionSet.find('.active').removeClass('active');
			$this.addClass('active');

			// Make option object dynamically
			var options = {},
			key = $optionSet.attr('data-option-key'),
			value = $this.attr('data-option-value');
			value = value === 'false' ? false : value;
			options[key] = value;
			$container.isotope( options );
			return false;
		});
	});
};

})();
