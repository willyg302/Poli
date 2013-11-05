//////////////////////////////////////////////////////////////////////////
//                                                                      //
// This is a generated file. You can view the original                  //
// source in your browser if your browser supports source maps.         //
//                                                                      //
// If you are using Chrome, open the Developer Tools and click the gear //
// icon in its lower right corner. In the General Settings panel, turn  //
// on 'Enable source maps'.                                             //
//                                                                      //
// If you are using Firefox 23, go to `about:config` and set the        //
// `devtools.debugger.source-maps-enabled` preference to true.          //
// (The preference should be on by default in Firefox 24; versions      //
// older than 23 do not support source maps.)                           //
//                                                                      //
//////////////////////////////////////////////////////////////////////////


(function () {

/* Imports */
var Meteor = Package.meteor.Meteor;
var Handlebars = Package.handlebars.Handlebars;
var Deps = Package.deps.Deps;

/* Package-scope variables */
var Helpers;

(function () {

///////////////////////////////////////////////////////////////////////
//                                                                   //
// packages\handlebar-helpers\handlebar-helpers.js                   //
//                                                                   //
///////////////////////////////////////////////////////////////////////
                                                                     //
// Helper scope                                                      // 1
if (typeof Helpers === 'undefined') {                                // 2
	Helpers = {};                                                       // 3
}                                                                    // 4
                                                                     // 5
if (typeof Handlebars !== 'undefined') {                             // 6
	Handlebars.registerHelper('comp', function (v1, operator, v2) {     // 7
		var isTrue = false;                                                // 8
		switch (operator) {                                                // 9
			case '===': isTrue = v1 === v2; break;                            // 10
			case '!==': isTrue = v1 !== v2; break;                            // 11
			case '<': isTrue = v1 < v2; break;                                // 12
			case '<=': isTrue = v1 <= v2; break;                              // 13
			case '>': isTrue = v1 > v2; break;                                // 14
			case '>=': isTrue = v1 >= v2; break;                              // 15
			case '||': isTrue = v1 || v2; break;                              // 16
			case '&&': isTrue = v1 && v2; break;                              // 17
		}                                                                  // 18
		return isTrue;                                                     // 19
		//return isTrue ? options.fn(this) : options.inverse(this);        // 20
	});                                                                 // 21
}                                                                    // 22
///////////////////////////////////////////////////////////////////////

}).call(this);


/* Exports */
if (typeof Package === 'undefined') Package = {};
Package['handlebar-helpers'] = {
  Helpers: Helpers
};

})();

//# sourceMappingURL=5b067daff67c4a33845bbeed92d0c587263168c8.map
