// Client-side JavaScript, bundled and sent to client

// Define Minimongo collections to match server/publish.js
Tiles = new Meteor.Collection("tiles");

var tilesHandle = Meteor.subscribe("tiles");

/* Seeded PRNG */

(function(a,b,c,d,e,f){function k(a){var b,c=a.length,e=this,f=0,g=e.i=e.j=0,h=e.S=[];for(c||(a=[c++]);d>f;)h[f]=f++;for(f=0;d>f;f++)h[f]=h[g=j&g+a[f%c]+(b=h[f])],h[g]=b;(e.g=function(a){for(var b,c=0,f=e.i,g=e.j,h=e.S;a--;)b=h[f=j&f+1],c=c*d+h[j&(h[f]=h[g=j&g+b])+(h[g]=b)];return e.i=f,e.j=g,c})(d)}function l(a,b){var e,c=[],d=(typeof a)[0];if(b&&"o"==d)for(e in a)try{c.push(l(a[e],b-1))}catch(f){}return c.length?c:"s"==d?a:a+"\0"}function m(a,b){for(var d,c=a+"",e=0;c.length>e;)b[j&e]=j&(d^=19*b[j&e])+c.charCodeAt(e++);return o(b)}function n(c){try{return a.crypto.getRandomValues(c=new Uint8Array(d)),o(c)}catch(e){return[+new Date,a,a.navigator.plugins,a.screen,o(b)]}}function o(a){return String.fromCharCode.apply(0,a)}var g=c.pow(d,e),h=c.pow(2,f),i=2*h,j=d-1;c.seedrandom=function(a,f){var j=[],p=m(l(f?[a,o(b)]:0 in arguments?a:n(),3),j),q=new k(j);return m(o(q.S),b),c.random=function(){for(var a=q.g(e),b=g,c=0;h>a;)a=(a+c)*d,b*=d,c=q.g(1);for(;a>=i;)a/=2,b/=2,c>>>=1;return(a+c)/b},p},m(c.random(),b)})(this,[],Math,256,6,52);

/* Prototypes */

Array.prototype.shuffle = function() {
	for (var j, x, i = this.length; i; j = parseInt(Math.random() * i), x = this[--i], this[i] = this[j], this[j] = x);
	return this;
};

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
	Math.seedrandom('Poli'); // Make sure to seed the PRNG the same way every time!
	return Tiles.find({}).fetch().shuffle();
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
		event.stopImmediatePropagation(); // Don't follow links!
	},
	'click': function (event) {
		// When we click on the tile (other than to bookmark), go to data source
		window.location = this.data.src;
	}
});

Template.page.events({
	'click .menu-toggle': function (event) {
		event.preventDefault();
		$("#wrapper").toggleClass("active");
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

UI.registerHelper('tileComp', function() {
	var type = this.valueOf();
	switch (type) {
		case 'facebook': return Template.fbtile;
		case 'youtube': return Template.yttile;
		case 'twitter': return Template.twtile;
		default: return Template.dftile;
	}
});