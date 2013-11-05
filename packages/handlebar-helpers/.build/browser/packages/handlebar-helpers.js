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
