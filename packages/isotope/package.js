// Isotope

Package.describe({
	summary: "An exquisite jQuery plugin for magical layouts"
});

Package.on_use(function (api) {
	api.use(['jquery'], 'client');
	api.add_files(['../../public/isotope/js/jquery.isotope.min.js'], 'client');
	api.add_files(['../../public/isotope/css/isotope.css'], 'client');
});