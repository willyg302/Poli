// This file exposes moment so that it works with Meteor's package system.
if (typeof Package !== "undefined") {
	moment = this.moment;
}