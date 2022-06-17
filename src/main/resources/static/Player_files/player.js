! function (e) {
	function t(i) {
		if (n[i]) return n[i].exports;
		var r = n[i] = {
			exports: {},
			id: i,
			loaded: !1
		};
		return e[i].call(r.exports, r, r.exports, t), r.loaded = !0, r.exports
	}
	var n = {};
	return t.m = e, t.c = n, t.p = "", t(0)
}([function (e, t, n) {
	n(1), e.exports = n(1)
}, function (e, t, n) {
	var i, r;
	i = [n(6), n(71), n(7), n(51), n(63), n(70), n(2), n(81), n(11), n(9), n(80)], r = function (e, t, n, i, r, o, a, s, c, u, l) {
			"use strict";

			function d(t) {
				s.validatePlayerDivId(t), this.version = e.NANOPLAYER, this.coreversion = "", this.viewversion = "", this.type = "default", this.id = Math.round(1e11 * Math.random()).toString(), this._playerDivId = t, this._core = null, this._view = null, this._metrics = null
			}
			var f = t.events,
				h = t.states,
				E = t.emptyConfig,
				p = t.validConfig,
				m = t.pauseReasons,
				_ = t.errorCodes,
				T = t.capabilities,
				y = n.events,
				v = n.emptyConfig,
				g = n.validConfig,
				S = r.emptyConfig,
				A = r.validConfig,
				N = (i.emptyConfig, i.validConfig, d.prototype = Object.create(c.prototype));
			return N.setup = function (e) {
				var n = l.copy(e);
				return new Promise(function (e, s) {
					try {
						var c = l && l.events && l.events.onError ? l.events.onError : null,
							u = function (e) {
								e.code || (e.code = _.SETUP.EXCEPTION), e.message || (e.message = "An unknown error occured during setup.");
								var t = {
									name: "Error",
									data: {
										code: e.code,
										message: e.message
									},
									player: this._playerDivId,
									id: this.id,
									version: this.version,
									state: h.IDLE
								};
								c && this.on(t.name, c), this.emit(t.name, t), this._initView(this._viewConfig), null !== this._view && this._view.handleView(d.states.UNINITIALIZED), s(e)
							}.bind(this),
							l = {};
						a.extend(n, l), this._bintuConfig = S.create(), this._coreConfig = E.create(), this._viewConfig = v.create(), a.merge(l, this._bintuConfig), a.clean(this._bintuConfig), a.merge(l, this._viewConfig), this.destroy(), l.metrics && (this._metrics = new i, this._metrics.version = this.version, this._metrics.setup(l, this));
						var f = {};
						a.extend(n, f), this._checkConfig(f), r.setup(l).then(function (n) {
							try {
								a.merge(n, this._viewConfig), a.clean(this._viewConfig), a.merge(n, this._coreConfig), a.clean(this._coreConfig), this._initView(this._viewConfig), this._core = new t(this._playerDivId), this._core.id = this.id, this._corePropagator = o.create(this._core, d.coreEvents, this), this._setPublicListeners(this._coreConfig.events, this), this._connectLayers(), this._core.setup(this._coreConfig).then(function (t) {
									window.removeEventListener("unload", this.destroy.bind(this)), window.addEventListener("unload", this.destroy.bind(this)), this.coreversion = this._core.version, this.type = this._core.type, this._viewConfig.style && this._viewConfig.style.view && this._view.postSetup(this._core._realPlayer._mediaElementId), a.extend(this._bintuConfig, t), a.extend(this._viewConfig, t), a.clean(t), e(t)
								}.bind(this), function (e) {
									null !== this._view && this._view.handleView(d.states.UNINITIALIZED), s(e)
								}.bind(this))
							} catch (e) {
								u(e)
							}
						}.bind(this), u)
					} catch (e) {
						u(e)
					}
				}.bind(this))
			}, N.destroy = function () {
				this._view && this._view.destroy(), this._view = null, this._core && this._core.destroy(), this._core = null, this._metrics && this._metrics.destroy(), this._metrics = null
			}, N._checkConfig = function (e) {
				var t = [];
				if (e.source && e.source.bintu) {
					var n = a.check({
						source: {
							bintu: e.source.bintu
						}
					}, A.create());
					t = t.concat(n)
				}
				if (e.style) {
					var i = a.check({
						style: e.style
					}, g.create());
					t = t.concat(i)
				}
				if (e.source) {
					delete e.source.bintu, delete e.style, delete e.metrics;
					var r = a.check(e, p.create()).filter(function (e) {
						return e.indexOf("valid") !== -1
					});
					t = t.concat(r)
				}
				if (t.length) {
					var o = e && e.events && e.events.onWarning ? e.events.onWarning : null;
					o && this.on(f.WARNING, o), t.forEach(function (e) {
						var t = {
							name: f.WARNING,
							data: {
								message: e
							},
							player: this._playerDivId,
							id: this.id,
							version: this.version,
							state: h.IDLE
						};
						this.emit(t.name, t)
					}.bind(this))
				}
			}, N._initView = function (e) {
				!this._view && this._viewConfig.style && this._viewConfig.style.view && (this._view = new n, this._view.version = this.version, this.viewversion = this._view.version, this._view.setBaseValues(this._playerDivId), this._view.baseSetup(e), this._viewPropagator = o.create(this._view, d.viewEvents, this))
			}, N._setPublicListeners = function (e, t) {
				for (var n in e)
					if (e.hasOwnProperty(n) && "function" == typeof e[n]) {
						var i = n.replace("on", ""),
							r = e[n];
						t.on(i, r), delete e[n]
					}
			}, N._connectLayers = function () {
				function e(e, t, n, i) {
					for (var r in e) e.hasOwnProperty(r) && t.indexOf(e[r]) === -1 && n.on(e[r], i.bind(this))
				}
				var t = [d.coreEvents.MEDIA];
				e(d.coreEvents, t, this, this._onCoreEvent.bind(this)), e(d.viewEvents, [], this, this._onViewEvent.bind(this))
			}, N._onCoreEvent = function (e) {
				if (this._view) {
					if (e.name === d.coreEvents.STATS) return void this._view.handleStats(e.data.stats);
					e.name === d.coreEvents.STATE_CHANGE && this._view.update(e), e.name === d.coreEvents.STREAM_INFO && this._view.handleStreamInfo(e), e.name === d.coreEvents.STREAM_INFO_UPDATE && this._view.handleStreamInfoUpdate(e), e.name !== d.coreEvents.MUTE && e.name !== d.coreEvents.UNMUTE || this._view.handleMute(e.name === d.coreEvents.MUTE), e.name === d.coreEvents.VOLUME_CHANGE && this._view.handleVolume(e.data.volume), e.name === d.coreEvents.METADATA && this._view.handleMetaData(e)
				}
			}, N._onViewEvent = function (e) {
				e.name === d.viewEvents.PLAY ? this._core.play() : e.name === d.viewEvents.PAUSE && this._core.pause(), e.name === d.viewEvents.MUTE ? this._core.mute() : e.name === d.viewEvents.UNMUTE && this._core.unmute(), e.name === d.viewEvents.VOLUME_CHANGE && this._core.setVolume(e.data.volume)
			}, N._callMethod = function () {
				if (!this._core) throw new Error('"' + arguments[0] + '" must be implemented or player not initialized by call setup.');
				return this._core[arguments[0]].apply(this._core, Array.prototype.slice.call(arguments, 1))
			}, N.play = function () {
				this._callMethod("play")
			}, N.pause = function () {
				this._callMethod("pause")
			}, N.mute = function () {
				this._callMethod("mute")
			}, N.unmute = function () {
				this._callMethod("unmute")
			}, N.setVolume = function (e) {
				this._callMethod("setVolume", e)
			}, N.updateSource = function (e) {
				return new Promise(function (t, n) {
					r.updateSource(e).then(function (e) {
						this._callMethod("updateSource", e.source).then(function (e) {
							t(e)
						}, function (e) {
							n(e)
						})
					}.bind(this), function (e) {
						var t = {
							name: "Error",
							data: {
								code: e.code,
								message: e.message
							},
							player: this._playerDivId,
							id: this.id,
							version: this.version,
							state: h.IDLE
						};
						this.emit(t.name, t), n(e)
					}.bind(this))
				}.bind(this))
			}, d.states = h, d.coreEvents = f, d.viewEvents = y, d.pauseReasons = m, d.capabilities = T, window.NanoPlayer = d, d
		}.apply(t, i),
		/**
		 * @license
		 * nanoStream Player
		 * Copyright (c) 2016-19 nanocosmos IT GmbH. All rights reserved.
		 * http://www.nanocosmos.de
		 * sales@nanocosmos.de
		 *
		 * LEGAL NOTICE:
		 * This material is subject to the terms and conditions defined in
		 * separate license conditions ('LICENSE.txt')
		 * All information contained herein is, and remains the property
		 * of nanocosmos GmbH and its suppliers if any. The intellectual and technical concepts
		 * contained herein are proprietary to nanocosmos GmbH, and are protected by trade secret
		 * or copyright law. Dissemination of this information or reproduction of this material
		 * is strictly forbidden unless prior written permission is obtained from nanocosmos.
		 * All modifications will remain property of nanocosmos.
		 */
		!(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(3), n(4), n(5)], r = function (e, t, n) {
		function i(e, t) {
			var n;
			for (n in e) e.hasOwnProperty(n) && t.hasOwnProperty(n) && ("object" != typeof e[n] || !Object.keys(e[n]).length || "object" == typeof t[n] && Object.keys(t[n]).length ? "object" == typeof e[n] ? i(e[n], t[n]) : t[n] = e[n] : r(e[n], t[n]))
		}

		function r(e, t) {
			var n;
			for (n in e) e.hasOwnProperty(n) && (!t[n] && (t[n] = {}), "object" == typeof e[n] ? r(e[n], t[n]) : t[n] = e[n])
		}

		function o(e) {
			var t;
			for (t in e) e.hasOwnProperty(t) && ("undefined" == typeof e[t] || "string" == typeof e[t] && 0 === e[t].length) && delete e[t], e.hasOwnProperty(t) && e[t] instanceof Object && o(e[t]);
			for (t in e)(e.hasOwnProperty(t) && ("undefined" == typeof e[t] || "string" == typeof e[t] && 0 === e[t].length) || "object" == typeof e[t] && "function" != typeof e[t] && 0 === Object.keys(e[t]).length) && delete e[t]
		}

		function a(e, t, n, i) {
			n = n || "", i = i || [];
			var r;
			for (r in e) e.hasOwnProperty(r) && t.hasOwnProperty(r) && ("object" == typeof e[r] && "object" == typeof t[r] ? i = a(e[r], t[r], n + r + ".", i) : typeof e[r] !== t[r] && "*" !== t[r] && void 0 !== e[r] && "" !== e[r] && i.push("Configuration warning. The config property '$property$' must be of type '$type$'.".replace("$property$", n + r).replace("$type$", t[r]))), e.hasOwnProperty(r) && !t.hasOwnProperty(r) && i.push("Configuration warning. The config property '$property$' is not a valid property. Please remove from config.".replace("$property$", n + r));
			return i
		}
		return {
			merge: i,
			clean: o,
			check: a,
			extend: r
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e, t) {
			var n = new window.Error(e || "");
			return n.code = t || 0, n
		}
		return e
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			CONFIG_RTMP: "Configuration error. Could not create player, the rtmp configuration is missing or incomplete. Add an rtmp url and streamname to the configuration.",
			CONFIG_TOKEN: "Configuration error. Could not create player, with this configuration an security token is required. Add an token to the configuration.",
			CONFIG_WSS: "Configuration error. Could not create player, the websocket server configuration is missing.",
			CONFIG_HLS: "Configuration error. Could not create player, the hls server configuration is missing.",
			CONFIG_METADATA: "Configuration error. Could not create player, the websocket server configuration for metadata is missing.",
			CONFIG_SOURCE: "The players source configuration is malformed or missing, please check documentation for more information or contact us."
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			PLAYER: {
				NO_RTMP_URL_SET: 1001,
				NO_SERVER_SET: 1002,
				NOT_CONFIGURED: 1003,
				NOT_PLAYING: 1004,
				INTERACTION_REQUIRED: 1005,
				BUFFER_CONFIG_INVALID: 1006,
				PLAYBACK_SUSPENDED: 1007,
				PLAYBACK_ERROR: 1008,
				VISIBILITY_HIDDEN: 1009
			},
			STREAM: {
				NOT_FOUND: 2001,
				MEDIA_NOT_AVAILABLE: 2002,
				NOT_ENOUGH_DATA: 2003,
				SOURCE_STOPPED: 2004,
				METADATA_STILL_PROCESSING: 2014,
				METADATA_NO_START_INDEX: 2013,
				METADATA_INVALID_JSON: 2012,
				METADATA_WRONG_INDEX: 2011
			},
			MEDIA: {
				ABORTED: 3001,
				DOWNLOAD_ERROR: 3002,
				DECODE_ERROR: 3003,
				NOT_SUPPORTED: 3004,
				SOURCE_ENDED: 3100
			},
			NETWORK: {
				COULD_NOT_ESTABLISH_CONNECTION: 4001,
				CONNECTION_ERROR: 4002,
				MAX_RECONNECTS_REACHED: 4003,
				RECONNECTION_CONFIG_INVALID: 4004
			},
			SETUP: {
				EXCEPTION: 5001,
				CLIENT_NOT_SUPPORTED: 5002,
				FORCE_NOT_SUPPORTED: 5003,
				SOURCE_NOT_SUPPORTED: 5004,
				CONFIG_RTMP: 5005,
				CONFIG_TOKEN: 5006,
				CONFIG_WSS: 5007,
				CONFIG_HLS: 5008,
				CONFIG_METADATA: 5009,
				EMBED_PLAYER: 5010
			}
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return {
			NANOPLAYER: "3.18.3"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(2), n(10), n(11), n(12), n(14), n(15), n(17), n(18), n(19), n(20), n(8), n(21), n(22), n(23), n(24), n(25), n(26), n(31), n(36), n(38), n(43)], r = function (e, t, n, i, r, o, a, s, c, u, l, d, f, h, E, p, m, _, T, y, v, g) {
		"use strict";

		function S() {
			this.version = ""
		}
		var A = (n.log, S.prototype = Object.create(i.prototype));
		return A.setBaseValues = function (e) {
			this.config = a.create(), this._playerDivId = e, this._emitter = new i, this._muted = !1, this._handleAutoplay = !1, this._isRestarting = !1, this.state = u.IDLE
		}, A.baseSetup = function (e) {
			t.merge(e, this.config), t.clean(this.config), this.config.style.view && (this.registerExternalEventHandler(), this._style = _.create(this._emitter, this._playerDivId, this.config.style), this.config.style.audioPlayer || (this.config.style.fullScreenControl && (this._fullscreen = v.create(this._playerDivId, this._emitter)), this._middleView = T.create(this._playerDivId), this._resizeListener = c.create(this._playerDivId, this._emitter))), this.config.style.controls && (this._controls = g.create(this._playerDivId, this._emitter, this.config)), this._handleAutoplay = this.config.playback.autoplay
		}, A.postSetup = function (e) {
			this._mediaElementId = e, this._mediaElement = this.getMediaElement(), this.config.style.view && this._style.update(this._mediaElement), this.config.style.interactive && !this.config.style.audioPlayer ? (this._interactive = m.create(this._playerDivId, this._mediaElement, this._emitter), this._interactive.update(this.state)) : this.config.style.controls && this._controls.show(), this.registerInternalEventHandler()
		}, A.getMediaElement = function () {
			var e = document.getElementById(this._mediaElementId);
			if (null === e && r.isIOS11) {
				var t = document.getElementById(this._playerDivId).querySelector("iframe");
				e = t.contentDocument.body.firstChild
			}
			return e
		}, A.registerExternalEventHandler = function () {
			for (var e in d) d.hasOwnProperty(e) && this._emitter.on(d[e], this._emit.bind(this, d[e]))
		}, A.registerInternalEventHandler = function () {
			if (this._interactive && (this._emitter.on(h.PLAYBUTTON_SINGLE_CLICK, function (e) {
					this.state !== u.READY && this.state !== u.PAUSED ? this._emitter.emit(d.PAUSE) : this._emitter.emit(d.PLAY)
				}.bind(this)), this._emitter.on(h.AUDIO_SINGLE_CLICK, function (e) {
					this._muted ? this._emitter.emit(d.UNMUTE) : this._emitter.emit(d.MUTE)
				}.bind(this)), this._emitter.on(h.PLAYER_SINGLE_CLICK, function (e) {
					this._controls && this._controls.appear()
				}.bind(this))), this._controls) {
				var e = [E.FULLSCREEN_ENTER, E.FULLSCREEN_EXIT];
				for (var t in E) E.hasOwnProperty(t) && d.hasOwnProperty(t) && (e.indexOf(E[t]) === -1 ? this._emitter.on(E[t], this._emitter.emit.bind(this._emitter, d[t])) : this._fullscreen && this._emitter.on(E[t], function (e) {
					this._fullscreen && this._fullscreen.change()
				}.bind(this)))
			}
			if (this._fullscreen)
				for (var t in f) f.hasOwnProperty(t) && (this._emitter.on(f[t], function (e) {
					this.handleFullscreen(e && e === f.FULLSCREEN_ENTER)
				}.bind(this).bind(this, f[t])), this._emitter.on(f[t], this._emitter.emit.bind(this._emitter, d[t])));
			this._resizeListener && this._emitter.on(d.PLAYER_RESIZE, this._onPlayerResize.bind(this))
		}, A.removeExternalEventHandler = function () {
			if (this._emitter)
				for (var e in d) d.hasOwnProperty(e) && this._emitter.removeAllListeners(d[e])
		}, A.removeInternalEventHandler = function () {
			if (this._emitter) {
				if (this._interactive && (this._emitter.removeAllListeners(h.PLAYER_SINGLE_CLICK), this._emitter.removeAllListeners(h.PLAYBUTTON_SINGLE_CLICK), this._emitter.removeAllListeners(h.AUDIO_SINGLE_CLICK)), this._controls)
					for (var e in E) E.hasOwnProperty(e) && d.hasOwnProperty(e) && this._emitter.removeAllListeners(E[e]);
				if (this._fullscreen)
					for (var e in f) f.hasOwnProperty(e) && this._emitter.removeAllListeners(f[e]);
				this._resizeListener && this._emitter.removeAllListeners(d.PLAYER_RESIZE)
			}
		}, A.destroy = function () {
			this.removeExternalEventHandler(), this.removeInternalEventHandler(), this._emitter = null, this._resizeListener && this._resizeListener.destroy(), this._resizeListener = null, this._style && this._style.destroy(), this._style = null, this._interactive && this._interactive.destroy(), this._interactive = null, this._scaling && this._scaling.destroy(), this._scaling = null, this._middleView && this._middleView.destroy(), this._middleView = null, this._controls && this._controls.destroy(), this._controls = null, this._fullscreen && this._fullscreen.destroy(), this._fullscreen = null
		}, A.update = function (e) {
			this.state = e.state, this._emitter.emit(o.STATE_CHANGE, e), this.handleView(e.state), this._interactive && this._interactive.update(e.state)
		}, A.handleView = function (e) {
			switch (this._viewTimeout && clearTimeout(this._viewTimeout), e) {
				case u.UNINITIALIZED:
					this._middleView && this._middleView.update(p.ERROR);
					break;
				case u.IDLE:
					this._middleView && this._middleView.update(p.NONE);
					break;
				case u.READY:
					this._middleView && this._middleView.update(p.PLAYBUTTON), this._controls && this._controls.play();
					break;
				case u.LOADING:
					this._middleView && this._middleView.update(p.LOADING), this._controls && this._controls.pause();
					break;
				case u.PLAYING:
					this.handleAudio(), this._controls && this._controls.pause();
					break;
				case u.PAUSED:
					this._isRestarting || (this._middleView && this._middleView.update(p.PLAYBUTTON), this._controls && this._controls.play()), this._isRestarting = !1;
					break;
				case u.BUFFERING:
					this._viewTimeout = setTimeout(function () {
						this._middleView && this._middleView.update(p.LOADING), this._controls && this._controls.pause()
					}.bind(this), 1e3);
					break;
				case u.UNKNOWN:
					this._middleView && this._middleView.update(p.NONE);
					break;
				case u.PLAYBACK_RESTARTING:
					this._isRestarting = !0, this._middleView && this._middleView.update(p.NONE)
			}
			this.state = e
		}, A.handleStreamInfo = function (e) {
			this._emitter.emit(o.STREAM_INFO, e), this.progressStreamInfo(e)
		}, A.handleStreamInfoUpdate = function (e) {
			this._emitter.emit(o.STREAM_INFO_UPDATE, e), this.progressStreamInfo(e)
		}, A.progressStreamInfo = function (e) {
			this.streamInfo = e.data.streamInfo, this._scaling && this._scaling.destroy() && (this._scaling = null), this.streamInfo.haveVideo && (this._scaling = y.create(this._playerDivId, this._mediaElement, this.streamInfo.videoInfo, this.config.style.scaling))
		}, A.handleAudio = function () {
			var e = this.state === u.PLAYING,
				t = this.streamInfo && this.streamInfo.haveAudio;
			e && !this._muted && t && (this._handleAutoplay = !1);
			var n = this.config.style.displayMutedAutoplay && this._muted && this._handleAutoplay,
				i = this.config.style.displayAudioOnly && this.streamInfo && !this.streamInfo.haveVideo;
			e && (t && n || i ? this._middleView && this._middleView.update(p.AUDIO, this._muted) : this._middleView && this._middleView.update(p.NONE))
		}, A.handleMute = function (e) {
			this._controls && (e ? this._controls.mute() : this._controls.unmute()), this._muted = e, this.handleAudio()
		}, A.handleVolume = function (e) {
			this._controls && this._controls.volume(e)
		}, A.handleFullscreen = function (e) {
			this._controls && this._controls.fullscreen(e)
		}, A.handleStats = function (e) {
			this._controls && this._controls.time(e.currentTime)
		}, A.handleMetaData = function (e) {
			if ("object" == typeof e.data.message && e.data.message.hasOwnProperty("nanoStreamStatus")) {
				var t = "object" == typeof e.data.message.nanoStreamStatus && e.data.message.nanoStreamStatus.hasOwnProperty("VideoRotation") ? e.data.message.nanoStreamStatus.VideoRotation : 0;
				this._scaling && this._scaling.update(this.config.style.scaling, t)
			}
		}, A._emit = function (e, t) {
			var n = {};
			t && t.name && t.data ? n = t : t ? (n.data = t, n.name = e || "unknown") : (n.data = {}, n.name = e || "unknown"), n.player = this._playerDivId, n.state = this.state, this.emit(n.name, n)
		}, A._onPlayerResize = function (e) {
			this.handleView(this.state), this._scaling && this._scaling.update(), this._controls && this._controls.size()
		}, S.events = d, S.emptyConfig = a, S.validConfig = s, S
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			PAUSE: "ClickPause",
			PLAY: "ClickPlay",
			FULLSCREEN_ENTER: "ClickFullscreenEnter",
			FULLSCREEN_EXIT: "ClickFullscreenExit",
			PLAYER_RESIZE: "PlayerResize",
			MUTE: "ClickMute",
			UNMUTE: "ClickUnmute",
			VOLUME_CHANGE: "ClickVolumeChange"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		"use strict";
		var e = "Unknown",
			t = "";
		if (screen.width) {
			var n = screen.width ? screen.width : "",
				i = screen.height ? screen.height : "";
			t += "" + n + " x " + i
		}
		var r, o, a, s = navigator.appVersion,
			c = navigator.userAgent,
			u = navigator.appName,
			l = "" + parseFloat(navigator.appVersion),
			d = parseInt(navigator.appVersion, 10);
		(o = c.indexOf("Opera")) !== -1 && (u = "Opera", l = c.substring(o + 6), (o = c.indexOf("Version")) !== -1 && (l = c.substring(o + 8))), (o = c.indexOf("OPR/")) !== -1 ? (u = "Opera", l = c.substring(o + 4)) : (o = c.indexOf("MSIE")) !== -1 ? (u = "Microsoft Internet Explorer", l = c.substring(o + 5)) : "Netscape" === u && (o = c.indexOf("Trident/")) !== -1 ? (u = "Microsoft Internet Explorer", l = c.substring(o + 8), (o = c.indexOf("rv:")) !== -1 && (l = c.substring(o + 3))) : "Netscape" === u && (o = c.indexOf("Edge/")) !== -1 ? (u = "Microsoft Edge", l = c.substring(o + 5)) : (o = c.indexOf("Chrome")) !== -1 ? (u = "Chrome", l = c.substring(o + 7)) : (o = c.indexOf("PhantomJS")) !== -1 ? (u = "PhantomJS", l = c.substring(o + 10)) : (o = c.indexOf("Safari")) !== -1 ? (u = "Safari", l = c.substring(o + 7), (o = c.indexOf("Version")) !== -1 && (l = c.substring(o + 8)), c.indexOf("CriOS") !== -1 && (u = "Chrome")) : (o = c.indexOf("Firefox")) !== -1 ? (u = "Firefox", l = c.substring(o + 8)) : (r = c.lastIndexOf(" ") + 1) < (o = c.lastIndexOf("/")) && (u = c.substring(r, o), l = c.substring(o + 1), u.toLowerCase() === u.toUpperCase() && (u = navigator.appName)), (a = l.indexOf(";")) !== -1 && (l = l.substring(0, a)), (a = l.indexOf(" ")) !== -1 && (l = l.substring(0, a)), (a = l.indexOf(")")) !== -1 && (l = l.substring(0, a)), d = parseInt("" + l, 10), isNaN(d) && (l = "" + parseFloat(navigator.appVersion), d = parseInt(navigator.appVersion, 10));
		var f = /Mobile|mini|Fennec|Android|iP(ad|od|hone)/.test(s),
			h = !!navigator.cookieEnabled;
		"undefined" != typeof navigator.cookieEnabled || h || (document.cookie = "testcookie", h = document.cookie.indexOf("testcookie") !== -1);
		var E = e,
			p = [{
				s: "Windows 3.11",
				r: /Win16/
			}, {
				s: "Windows 95",
				r: /(Windows 95|Win95|Windows_95)/
			}, {
				s: "Windows ME",
				r: /(Win 9x 4.90|Windows ME)/
			}, {
				s: "Windows 98",
				r: /(Windows 98|Win98)/
			}, {
				s: "Windows CE",
				r: /Windows CE/
			}, {
				s: "Windows 2000",
				r: /(Windows NT 5.0|Windows 2000)/
			}, {
				s: "Windows XP",
				r: /(Windows NT 5.1|Windows XP)/
			}, {
				s: "Windows Server 2003",
				r: /Windows NT 5.2/
			}, {
				s: "Windows Vista",
				r: /Windows NT 6.0/
			}, {
				s: "Windows 7",
				r: /(Windows 7|Windows NT 6.1)/
			}, {
				s: "Windows 8.1",
				r: /(Windows 8.1|Windows NT 6.3)/
			}, {
				s: "Windows 8",
				r: /(Windows 8|Windows NT 6.2)/
			}, {
				s: "Windows 10",
				r: /(Windows 10|Windows NT 10.0)/
			}, {
				s: "Windows NT 4.0",
				r: /(Windows NT 4.0|WinNT4.0|WinNT|Windows NT)/
			}, {
				s: "Windows ME",
				r: /Windows ME/
			}, {
				s: "Android",
				r: /Android/
			}, {
				s: "Open BSD",
				r: /OpenBSD/
			}, {
				s: "Sun OS",
				r: /SunOS/
			}, {
				s: "Linux",
				r: /(Linux|X11)/
			}, {
				s: "iOS",
				r: /(iPhone|iPad|iPod)/
			}, {
				s: "Mac OS X",
				r: /Mac OS X/
			}, {
				s: "Mac OS",
				r: /(MacPPC|MacIntel|Mac_PowerPC|Macintosh)/
			}, {
				s: "QNX",
				r: /QNX/
			}, {
				s: "UNIX",
				r: /UNIX/
			}, {
				s: "BeOS",
				r: /BeOS/
			}, {
				s: "OS/2",
				r: /OS\/2/
			}, {
				s: "Search Bot",
				r: /(nuhk|Googlebot|Yammybot|Openbot|Slurp|MSNBot|Ask Jeeves\/Teoma|ia_archiver)/
			}];
		for (var m in p) {
			var _ = p[m];
			if (_.r.test(c)) {
				E = _.s;
				break
			}
		}
		var T = e;
		switch (/Windows/.test(E) && (/Windows (.*)/.exec(E) && (T = /Windows (.*)/.exec(E)[1]), E = "Windows"), E) {
			case "Mac OS X":
				/Mac OS X (10[._\d]+)/.exec(c) && (T = /Mac OS X (10[._\d]+)/.exec(c)[1]);
				break;
			case "Android":
				/Android ([._\d]+)/.exec(c) && (T = /Android ([._\d]+)/.exec(c)[1]);
				break;
			case "iOS":
				T = /OS (\d+)_(\d+)_?(\d+)?/.exec(s), T = T ? T[1] + "." + T[2] + "." + (0 | T[3]) : e
		}
		var y = {
			screen: t,
			browser: u,
			browserVersion: l,
			mobile: f,
			os: E,
			osVersion: T,
			cookies: h
		};
		return y
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = function () {
			function e() {
				var e = isNaN(n("debug")) ? 0 : +n("debug"),
					i = +t("nanoDebug");
				return Math.max(e, i)
			}

			function t(e) {
				return document.cookie.indexOf(e + "=") !== -1 ? document.cookie.split(e + "=")[1].split(";")[0] : null
			}

			function n(e) {
				if (!o) {
					o = [];
					var t = document.location.search.substr(1, document.location.search.length);
					if ("" === t && document.location.href.indexOf("?") !== -1) {
						var n = document.location.href.indexOf("?") + 1;
						t = document.location.href.slice(n)
					}
					if ("" !== t)
						for (var i = t.split("&"), r = 0; r < i.length; ++r) {
							var a = "",
								s = i[r].split("="),
								c = s[0];
							s.length > 1 && (a = s[1]), o[decodeURIComponent(c)] = decodeURIComponent(a)
						}
				}
				try {
					return o[e]
				} catch (e) {
					return
				}
			}

			function i() {
				return (new Date).toISOString()
			}
			var r = e(),
				o = void 0;
			setInterval(function () {
				r = e()
			}, 5e3);
			var a = function (e, t) {
				try {
					r >= t && console.debug(i() + ": " + e)
				} catch (e) {}
			};
			return {
				log: a,
				getHTTPParam: n
			}
		}();
		return e
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
			"use strict";

			function e() {}

			function t(e, t) {
				for (var n = e.length; n--;)
					if (e[n].listener === t) return n;
				return -1
			}

			function n(e) {
				return function () {
					return this[e].apply(this, arguments)
				}
			}
			var i = e.prototype;
			return i.getListeners = function (e) {
				var t, n, i = this._getEvents();
				if (e instanceof RegExp) {
					t = {};
					for (n in i) i.hasOwnProperty(n) && e.test(n) && (t[n] = i[n])
				} else t = i[e] || (i[e] = []);
				return t
			}, i.flattenListeners = function (e) {
				var t, n = [];
				for (t = 0; t < e.length; t += 1) n.push(e[t].listener);
				return n
			}, i.getListenersAsObject = function (e) {
				var t, n = this.getListeners(e);
				return n instanceof Array && (t = {}, t[e] = n), t || n
			}, i.addListener = function (e, n) {
				var i, r = this.getListenersAsObject(e),
					o = "object" == typeof n;
				for (i in r) r.hasOwnProperty(i) && t(r[i], n) === -1 && r[i].push(o ? n : {
					listener: n,
					once: !1
				});
				return this
			}, i.on = n("addListener"), i.addOnceListener = function (e, t) {
				return this.addListener(e, {
					listener: t,
					once: !0
				})
			}, i.once = n("addOnceListener"), i.defineEvent = function (e) {
				return this.getListeners(e), this
			}, i.defineEvents = function (e) {
				for (var t = 0; t < e.length; t += 1) this.defineEvent(e[t]);
				return this
			}, i.removeListener = function (e, n) {
				var i, r, o = this.getListenersAsObject(e);
				for (r in o) o.hasOwnProperty(r) && (i = t(o[r], n), i !== -1 && o[r].splice(i, 1));
				return this
			}, i.off = n("removeListener"), i.addListeners = function (e, t) {
				return this.manipulateListeners(!1, e, t)
			}, i.removeListeners = function (e, t) {
				return this.manipulateListeners(!0, e, t)
			}, i.manipulateListeners = function (e, t, n) {
				var i, r, o = e ? this.removeListener : this.addListener,
					a = e ? this.removeListeners : this.addListeners;
				if ("object" != typeof t || t instanceof RegExp)
					for (i = n.length; i--;) o.call(this, t, n[i]);
				else
					for (i in t) t.hasOwnProperty(i) && (r = t[i]) && ("function" == typeof r ? o.call(this, i, r) : a.call(this, i, r));
				return this
			}, i.removeEvent = function (e) {
				var t, n = typeof e,
					i = this._getEvents();
				if ("string" === n) delete i[e];
				else if (e instanceof RegExp)
					for (t in i) i.hasOwnProperty(t) && e.test(t) && delete i[t];
				else delete this._events;
				return this
			}, i.removeAllListeners = n("removeEvent"), i.emitEvent = function (e, t) {
				var n, i, r, o, a, s = this.getListenersAsObject(e);
				for (o in s)
					if (s.hasOwnProperty(o))
						for (n = s[o].slice(0), r = n.length; r--;) i = n[r], i.once === !0 && this.removeListener(e, i.listener), a = i.listener.apply(this, t || []), a === this._getOnceReturnValue() && this.removeListener(e, i.listener);
				return this
			}, i.trigger = n("emitEvent"), i.emit = function (e) {
				var t = Array.prototype.slice.call(arguments, 1);
				return t.length ? t.length && "object" != typeof t[0] ? t[0] = {
					name: e,
					data: t[0]
				} : t.length && !t[0].hasOwnProperty("data") ? t[0] = {
					name: e,
					data: t[0]
				} : t[0].name = e : t.push({
					name: e,
					data: {}
				}), this.emitEvent(e, t)
			}, i.emitSimple = function (e, t, n) {
				return this.emitEvent(e, [{
					name: e || "AnonymousEvent",
					target: n || this,
					data: t || {}
				}])
			}, i.setOnceReturnValue = function (e) {
				return this._onceReturnValue = e, this
			}, i._getOnceReturnValue = function () {
				return !this.hasOwnProperty("_onceReturnValue") || this._onceReturnValue
			}, i._getEvents = function () {
				return this._events || (this._events = {})
			}, e
		}.apply(t, i),
		/*!
		 * EventEmitter v4.2.11 - git.io/ee
		 * Unlicense - http://unlicense.org/
		 * Oliver Caldwell - http://oli.me.uk/
		 * @preserve
		 */
		!(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(13)], r = function (e, t) {
		var n = t.create("Conditions"),
			i = e.os,
			r = e.osVersion,
			o = e.browser,
			a = parseInt(e.browserVersion.split(".")[0], 10),
			s = "Microsoft Edge" === o,
			c = "Microsoft Internet Explorer" === o || s,
			u = "Chrome" === o && a > 57,
			l = "iOS" === i && a >= 11,
			d = "Firefox" === o,
			f = "Safari" === o,
			h = f && 12.1 === parseFloat(e.browserVersion) && "iOS" !== i,
			E = "iOS" === i && parseInt(r) >= 10,
			p = h,
			m = "iOS" !== i && !!window.MediaSource && (d && a >= 48 || s || window.MediaSource.isTypeSupported && window.MediaSource.isTypeSupported('video/mp4; codecs="avc1.42E01E, mp4a.40.2"')),
			_ = function () {
				var e = !1;
				try {
					"Chrome" === o && a < 54 ? e = !0 : "Firefox" === o && a < 48 ? e = !0 : "Microsoft Internet Explorer" === o && (a < 11 || 11 === a && 7 === parseInt(r)) ? e = !0 : "Safari" === o && a < 10 && (e = !0)
				} catch (e) {}
				return e
			}(),
			T = {
				mustUseHLS: E,
				canUseHLS: p,
				isIEorEdge: c,
				isChromeHigher57: u,
				hasMediaSource: m,
				isIOS11: l,
				isFirefox: d,
				isSafari: f,
				isSafari121MacOSX: h,
				isH5LiveKnownUnsupported: _
			};
		for (var y in T) T.hasOwnProperty(y) && n.debug(y + ": " + T[y]);
		return T
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(10)], r = function (e) {
		function t(t, n, i) {
			e.log("[" + n + "] " + i, t)
		}

		function n(e) {
			return {
				debug: t.bind(this, a, e),
				info: t.bind(this, o, e),
				warn: t.bind(this, r, e),
				error: t.bind(this, i, e),
				detail: t.bind(this, s, e)
			}
		}
		var i = 0,
			r = 1,
			o = 2,
			a = 3,
			s = 4;
		return {
			create: n
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			READY: "Ready",
			ERROR: "Error",
			PAUSE: "Pause",
			LOADING: "Loading",
			START_BUFFERING: "StartBuffering",
			STOP_BUFFERING: "StopBuffering",
			PLAY: "Play",
			METADATA: "MetaData",
			STATS: "Stats",
			PLAYBACK_FINISHED: "PlaybackFinished",
			MEDIA: "Media",
			STREAM_INFO: "StreamInfo",
			STREAM_INFO_UPDATE: "StreamInfoUpdate",
			MUTE: "Mute",
			UNMUTE: "Unmute",
			VOLUME_CHANGE: "VolumeChange",
			STATE_CHANGE: "StateChange",
			WARNING: "Warning",
			DESTROY: "Destroy"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(2), n(16)], r = function (e, t) {
		function n() {
			var n = t.create();
			return e.extend({
				style: {
					width: void 0,
					height: void 0,
					aspectratio: void 0,
					controls: !0,
					interactive: !0,
					view: !0,
					scaling: "letterbox",
					keepFrame: !0,
					displayAudioOnly: !0,
					audioPlayer: !1,
					displayMutedAutoplay: !0,
					backgroundColor: "black",
					fullScreenControl: !0
				}
			}, n), n
		}
		return {
			create: n
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			return {
				playback: {
					autoplay: !0,
					automute: !1,
					muted: !1,
					metadata: !1,
					forceTech: "",
					flashplayer: "",
					videoId: "",
					keepConnection: !1,
					allowSafariHlsFallback: !1,
					reconnect: {
						minDelay: 2,
						maxDelay: 10,
						delaySteps: 10,
						maxRetries: 10
					},
					timeouts: {
						loading: 20,
						buffering: 20,
						connecting: 5
					}
				},
				source: {
					hls: "",
					h5live: {
						server: {
							websocket: "",
							progressive: "",
							hls: ""
						},
						token: "",
						rtmp: {
							url: "",
							streamname: ""
						},
						security: {
							token: "",
							expires: "",
							options: "",
							tag: ""
						},
						params: {}
					}
				},
				events: {
					onReady: void 0,
					onPlay: void 0,
					onPause: void 0,
					onLoading: void 0,
					onStartBuffering: void 0,
					onStopBuffering: void 0,
					onError: void 0,
					onMetaData: void 0,
					onFullscreenEnter: void 0,
					onFullscreenExit: void 0,
					onPlaybackFinished: void 0,
					onMedia: void 0,
					onStats: void 0,
					onMute: void 0,
					onUnmute: void 0,
					onVolumeChange: void 0,
					onStreamInfo: void 0,
					onStreamInfoUpdate: void 0,
					onWarning: void 0,
					onDestroy: void 0
				},
				tweaks: {
					buffer: {
						min: void 0,
						start: void 0,
						max: void 0,
						target: void 0,
						limit: void 0
					},
					bufferDynamic: {
						offsetThreshold: void 0,
						offsetStep: void 0,
						cooldownTime: void 0
					}
				}
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			return {
				style: {
					width: "string",
					height: "string",
					aspectratio: "string",
					controls: "boolean",
					interactive: "boolean",
					view: "boolean",
					scaling: "string",
					keepFrame: "boolean",
					displayAudioOnly: "boolean",
					audioPlayer: "boolean",
					displayMutedAutoplay: "boolean",
					backgroundColor: "string",
					fullScreenControl: "boolean"
				}
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(8)], r = function (e) {
		"use strict";

		function t(t, n) {
			function i() {
				!o || o.clientWidth === a && o.clientHeight === s || (a = o.clientWidth, s = o.clientHeight, u.emit(e.PLAYER_RESIZE, {
					width: a,
					height: s,
					aspectratio: a / s
				}))
			}

			function r() {
				return c && clearInterval(c), c = 0, o = null, null
			}
			var o = document.getElementById(t),
				a = o.clientWidth,
				s = o.clientHeight,
				c = 0,
				u = n;
			return c = setInterval(i, 100), {
				destroy: r
			}
		}
		return {
			create: t
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			UNINITIALIZED: 1,
			IDLE: 2,
			READY: 3,
			LOADING: 4,
			PLAYING: 5,
			PAUSED: 6,
			BUFFERING: 7,
			UNKNOWN: 8,
			PLAYBACK_NOT_STARTED: 9,
			PLAYBACK_SUSPENDED: 10,
			PAUSING: 11,
			PLAYBACK_ERROR: 12,
			RECONNECTION_IMMINENT: 13,
			CONNECTION_ERROR: 14,
			DESTROYING: 15,
			PLAYBACK_RESTARTING: 16
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			CLICK: "click",
			DOUBLE_CLICK: "dblclick",
			TOUCH_START: "touchstart",
			TOUCH_END: "touchend",
			MOUSE_DOWN: "mousedown",
			MOUSE_UP: "mouseup",
			MOUSE_ENTER: "mouseenter",
			MOUSE_LEAVE: "mouseleave",
			MOUSE_MOVE: "mousemove"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			FULLSCREEN_ENTER: "FullscreenEnter",
			FULLSCREEN_EXIT: "FullscreenExit"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			PLAYER_SINGLE_CLICK: "PlayerSingleClick",
			PLAYER_DOUBLE_CLICK: "PlayerDoubleClick",
			PLAYBUTTON_SINGLE_CLICK: "PlaybuttonSingleClick",
			AUDIO_SINGLE_CLICK: "AudioSingleClick"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			PLAY: "ControlsPlay",
			PAUSE: "ControlsPause",
			MUTE: "ControlsMute",
			UNMUTE: "ControlsUnmute",
			VOLUME_CHANGE: "ControlsVolumeChange",
			FULLSCREEN_ENTER: "ControlsFullscreenEnter",
			FULLSCREEN_EXIT: "ControlsFullscreenExit"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			NONE: "none",
			LOADING: "loading",
			PLAYBUTTON: "playbutton",
			ERROR: "error",
			AUDIO: "audio"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(22), n(8), n(20), n(19)], r = function (e, t, n, i, r) {
		"use strict";

		function o(e, n, r) {
			function o() {
				f.addEventListener(i.CLICK, c), n.addEventListener(i.CLICK, c), n.addEventListener("contextmenu", u)
			}

			function a() {
				f && f.removeEventListener(i.CLICK, c), n && (n.removeEventListener(i.CLICK, c), n.removeEventListener("contextmenu", u))
			}

			function s(t) {
				d = t;
				var n = document.getElementById("middleView-" + e);
				n && n.firstChild && (n.firstChild.addEventListener(i.CLICK, c), a(), setTimeout(o, 200))
			}

			function c(e) {
				e.preventDefault(), e.stopPropagation ? e.stopPropagation() : e.cancelBubble = !0, e.target.id && e.target.id.indexOf("controls-") !== -1 || e.button && 0 !== e.button || h.touchInProgress || (h.touchInProgress = !0, e.target.id && e.target.id.indexOf("playButton-") !== -1 ? r.emit(t.PLAYBUTTON_SINGLE_CLICK) : e.target.id && e.target.id.indexOf("audio-") !== -1 ? r.emit(t.AUDIO_SINGLE_CLICK) : r.emit(t.PLAYER_SINGLE_CLICK), h.timeout = setTimeout(function () {
					h.touchInProgress = !1, clearTimeout(h.timeout), h.timeout = 0
				}, 300))
			}

			function u(e) {
				return e.preventDefault(), !1
			}

			function l() {
				return a(), h.timeout && clearTimeout(h.timeout), h.timeout = 0, n = null, f = null, null
			}
			var d, f = document.getElementById(e),
				h = {
					timeout: 0,
					touchInProgress: !1
				};
			return {
				update: s,
				destroy: l
			}
		}
		return {
			create: o
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(27), n(14), n(28), n(29)], r = function (e, t, n, i, r) {
		"use strict";

		function o(o, a, s) {
			function c(t) {
				h = document.getElementById(a);
				var n, i, r, o, s, c = ["width", "height"],
					u = 0;
				"auto" !== t.width && "auto" !== t.height ? t.audioPlayer ? t.width && t.height ? (h.style.width = t.width, h.style.height = t.height) : t.width ? (h.style.width = t.width, h.style.height = Math.round(e.mobile ? 100 / 6 : 100 / 12) / 100 * h.clientWidth + "px") : h.clientWidth || h.clientHeight ? h.clientWidth && (h.style.height = Math.round(e.mobile ? 100 / 6 : 100 / 12) / 100 * h.clientWidth + "px") : (h.style.width = "640px", h.style.height = Math.round(e.mobile ? 100 / 6 : 100 / 12) / 100 * h.clientWidth + "px") : t.width && t.height ? (h.style.width = t.width, h.style.height = t.height) : (t.width || t.height) && t.aspectratio ? (u = +!!t.height, r = t[c[u]].indexOf("%") !== -1, o = t[c[u]].indexOf("px") !== -1, h.style[c[u]] = t[c[u]], s = t.aspectratio.split("/"), s[0] = parseInt(s[0]), s[1] = parseInt(s[1]), (r || o) && (n = parseInt(t[c[u]].replace(r ? "%" : "px", "")), i = Math.round(n * s[+!u] / s[u]).toString() + (r ? "%" : "px"), h.style[c[+!u]] = i)) : (t.width || t.height) && (t.width || t.aspectratio) && (t.aspectratio || t.height) || h.clientWidth && h.clientHeight || (h.style.width = "640px", h.style.height = "360px") : h.clientWidth || h.clientHeight ? h.clientWidth || "auto" !== t.width ? h.clientHeight || (t.aspectratio && !t.audioPlayer ? (s = t.aspectratio.split("/"), s[0] = parseInt(s[0]), s[1] = parseInt(s[1]), h.style.height = Math.round(h.clientWidth / s[0] * s[1]).toString() + "px") : h.style.height = t.audioPlayer ? Math.round(e.mobile ? 100 / 6 : 100 / 12) / 100 * h.clientWidth + "px" : Math.round(h.clientWidth / 16 * 9).toString() + "px") : t.aspectratio ? (s = t.aspectratio.split("/"), s[0] = parseInt(s[0]), s[1] = parseInt(s[1]), h.style.width = Math.round(h.clientHeight * s[0] / s[1]).toString() + "px") : h.style.width = Math.round(16 * h.clientHeight / 9).toString() + "px" : (h.style.width = "640px", h.style.height = t.audioPlayer ? Math.round(e.mobile ? 100 / 6 : 100 / 12) / 100 * h.clientWidth + "px" : "360px"), h.style.overflow = "hidden", h.style.position = "relative", h.style.backgroundColor = t.audioPlayer ? "transparent" : t.backgroundColor ? t.backgroundColor : "black"
			}

			function u(e) {
				p = e, p && (!_ && r.add({
					target: o,
					listeners: m
				}), p.style.width = "100%", p.style.height = "100%", p.style.position = "absolute", p.style.left = "50%", p.style.top = "50%", p.style.marginRight = "-50%", p.style.backgroundColor = s.audioPlayer ? "transparent" : s.backgroundColor ? s.backgroundColor : "black", t.normalize(p, "transform", "translate(-50%, -50%)"))
			}

			function l() {
				return r.remove({
					target: o,
					listeners: m
				}), h = null, p = null, null
			}

			function d(e) {
				E = e.state, s.audioPlayer ? p.style.visibility = "hidden" : E == i.READY ? !s.keepFrame && (p.style.visibility = "hidden") : E == i.PAUSED ? !s.keepFrame && (p.style.visibility = "hidden") : E == i.PLAYING && !s.keepFrame && (p.style.visibility = "visible")
			}

			function f(e) {
				s.audioPlayer && (p.style.visibility = "hidden")
			}
			var h, E, p, m = [{
					type: n.STATE_CHANGE,
					listener: d
				}, {
					type: n.STREAM_INFO,
					listener: f
				}],
				_ = !1;
			return c(s), {
				update: u,
				destroy: l
			}
		}
		return {
			create: o
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e, n, i) {
			for (var r = t(n), o = "", a = 0; a < r.length; a += 1)
				if (o = r[a], o in e.style) {
					e.style[o] = i;
					break
				}
		}

		function t(e) {
			var t = e.length - 1,
				n = e.substr(0, 1).toUpperCase() + e.substr(1, t);
			return ["webkit" + n, "moz" + n, "ms" + n, e]
		}
		return {
			normalize: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			UNINITIALIZED: 1,
			IDLE: 2,
			READY: 3,
			LOADING: 4,
			PLAYING: 5,
			PAUSED: 6,
			BUFFERING: 7,
			UNKNOWN: 8,
			PLAYBACK_NOT_STARTED: 9,
			PLAYBACK_SUSPENDED: 10,
			PAUSING: 11,
			PLAYBACK_ERROR: 12,
			RECONNECTION_IMMINENT: 13,
			CONNECTION_ERROR: 14,
			DESTROYING: 15,
			PLAYBACK_RESTARTING: 16,
			VISIBILITY_HIDDEN: 17,
			NOT_ENOUGH_DATA: 18,
			SOURCE_STREAM_STOPPED: 19
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(30)], r = function (e) {
		"use strict";

		function t(t) {
			if (!t.target) throw new Error(e.NO_TARGET);
			if (!t.listeners || !t.listeners.length) throw new Error(e.NO_LISTENERS);
			if (!n(t.target, o) || !n(t.target, a)) throw new Error(e.NOT_DISPATCHER);
			for (var i = 0; i < t.listeners.length; ++i)
				if (!t.listeners[i].type && "string" != typeof t.listeners[i].type || 0 !== t.listeners[i].type.length) {
					if (!t.listeners[i].type) throw new Error(e.MISSING_TYPE.replace("%index%", i));
					if ("function" != typeof t.listeners[i].listener) throw new Error(e.MISSING_LISTENER.replace("%index%", i))
				} else t.listeners.splice(i, 1), i--
		}

		function n(e, t) {
			for (var n = 0; n < t.length; ++n)
				if ("function" == typeof e[t[n]]) return e[t[n]];
			return null
		}

		function i(e, i) {
			t(i), r(n(i.target, e), i.target, i.listeners)
		}

		function r(e, t, n) {
			for (var i = 0; i < n.length; ++i) e.call(t, n[i].type, n[i].listener)
		}
		var o = ["addEventListener", "addListener", "on"],
			a = ["removeEventListener", "removeListener", "off"];
		return {
			add: i.bind(null, o),
			remove: i.bind(null, a)
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			NO_TARGET: "No target in config",
			NO_LISTENERS: "No listeners in config",
			NOT_DISPATCHER: "Target is not an event dispatcher",
			MISSING_TYPE: "Missing type in listener #%index%",
			MISSING_LISTENER: "Missing/invalid listener in listener #%index%"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(27), n(24), n(32), n(33), n(34), n(35)], r = function (e, t, n, i, r, o) {
		"use strict";

		function a(a) {
			function s() {
				if (!document.getElementById(p)) {
					var t = u();
					m.id = p, m.style.width = t.width + "px", m.style.height = t.height + "px", m.style.position = "absolute", m.style.left = "50%", m.style.top = "50%", m.style.marginRight = "-50%", e.normalize(m, "transform", "translate(-50%, -50%)"), m.style.zIndex = 1e3, E.appendChild(m)
				}
			}

			function c() {
				m && m.parentElement === E && E.removeChild(m)
			}

			function u() {
				var e = document.getElementById(a),
					t = .2,
					n = Math.round(Math.min(e.clientWidth, e.clientHeight) * t);
				return {
					width: n,
					height: n
				}
			}

			function l(e, n) {
				h && h.destroy && (h.destroy(), h = null), e && e !== t.NONE ? (s(), h = f[e].create(a, m, u, n)) : c()
			}

			function d() {
				return h && h.destroy && (h.destroy(), h = null), c(), m = null, E = null, null
			}
			var f = {
					loading: n,
					playbutton: i,
					error: r,
					audio: o
				},
				h = null,
				E = document.getElementById(a),
				p = "middleView-" + a,
				m = document.createElement("div");
			return {
				update: l,
				destroy: d
			}
		}
		return {
			create: a
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		"use strict";

		function e(e, t, n) {
			function i() {
				var e = n();
				for ((t.style.width = e.width + "px") && (o.width = t.clientWidth), (t.style.height = e.height + "px") && (o.height = t.clientHeight), a.xCenter = o.width / 2, a.yCenter = o.height / 2, a.radius = o.width / 3, a.startSize = a.radius / 3, a.num++, a.ctx.clearRect(0, 0, 2 * a.xCenter, 2 * a.yCenter), a.i = 0; a.i < 9; a.i++) a.ctx.beginPath(), a.ctx.fillStyle = "rgba(255,255,255," + .1 * a.i + ")", a.angle = a.pi * a.i * .25, a.posX[a.i] = a.xCenter + a.radius * Math.cos(a.angle), a.posY[a.i] = a.yCenter + a.radius * Math.sin(a.angle), a.ctx.arc(a.posX[(a.i + a.num) % 8], a.posY[(a.i + a.num) % 8], a.startSize / 9 * a.i, 0, 2 * a.pi, 1), a.ctx.fill()
			}

			function r() {
				return a.interval && clearInterval(a.interval), a.interval = 0, a.ctx && a.ctx.clearRect(0, 0, o.width, o.height), o.parentElement === t && t.removeChild(o), o = null, null
			}
			var o = document.createElement("canvas"),
				a = {
					id: "loadingSign-" + e,
					interval: setInterval(i, 100),
					ctx: o.getContext("2d"),
					pi: Math.PI,
					xCenter: void 0,
					yCenter: void 0,
					radius: void 0,
					startSize: void 0,
					num: 5,
					posX: [],
					posY: [],
					angle: void 0,
					size: void 0,
					i: void 0
				};
			return o.id = a.id, o.style.zIndex = 1001, t.appendChild(o), {
				destroy: r
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		"use strict";

		function e(e, t, n) {
			function i() {
				var e = n();
				(t.style.width = e.width + "px") && (o.width = t.clientWidth), (t.style.height = e.height + "px") && (o.height = t.clientHeight);
				var i = o.width;
				a.ctx.beginPath(), a.ctx.fillStyle = "transparent", a.ctx.arc(.5 * i, .5 * i, .45 * i, 0, 2 * Math.PI), a.ctx.fill(), a.ctx.lineWidth = .05 * i, a.ctx.strokeStyle = "rgba(255,255,255,0.7)", a.ctx.stroke(), a.ctx.fillStyle = "rgba(255,255,255,0.7)", a.ctx.beginPath(), a.ctx.moveTo(.35 * i, .25 * i), a.ctx.lineTo(.35 * i, .75 * i), a.ctx.lineTo(.75 * i, .5 * i), a.ctx.fill()
			}

			function r() {
				return a.ctx && a.ctx.clearRect(0, 0, o.width, o.height), o.parentElement === t && t.removeChild(o), o = null, null
			}
			var o = document.createElement("canvas"),
				a = {
					id: "playButton-" + e,
					ctx: o.getContext("2d")
				};
			return o.id = a.id, o.style.zIndex = 1001, t.appendChild(o), i(), {
				destroy: r
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		"use strict";

		function e(e, t, n) {
			function i() {
				var e = n();
				(t.style.width = e.width + "px") && (o.width = t.clientWidth), (t.style.height = e.height + "px") && (o.height = t.clientHeight);
				var i = o.width,
					r = .8 * i,
					s = .7 * i,
					c = .1 * i,
					u = "#ffc821",
					l = "#faf100",
					d = "#dcaa09",
					f = .05 * i;
				a.ctx.beginPath(), a.ctx.moveTo(c + r / 2, c), a.ctx.lineTo(c + r, s + c), a.ctx.lineTo(c, s + c), a.ctx.closePath();
				var h = a.ctx.createLinearGradient(0, 0, 0, s);
				h.addColorStop(0, u), h.addColorStop(1, l), a.ctx.lineWidth = 2 * f, a.ctx.lineJoin = "round", a.ctx.strokeStyle = h, a.ctx.stroke(), a.ctx.fillStyle = h, a.ctx.fill(), h = a.ctx.createLinearGradient(0, c, 0, c + s), h.addColorStop(0, "transparent"), h.addColorStop(.5, "transparent"), h.addColorStop(.5, d), h.addColorStop(1, l), a.ctx.fillStyle = h, a.ctx.fill(), a.ctx.lineWidth = f, a.ctx.lineJoin = "round", a.ctx.strokeStyle = "#333", a.ctx.stroke(), a.ctx.textAlign = "center", a.ctx.textBaseline = "middle", a.ctx.font = "bold " + Math.round(s / 1.3) + 'px "Times New Roman", Times, serif', a.ctx.fillStyle = "#333";
				try {
					a.ctx.fillText("!", c + r / 2, c + s / 1.7)
				} catch (e) {}
			}

			function r() {
				return a.ctx && a.ctx.clearRect(0, 0, o.width, o.height), o.parentElement === t && t.removeChild(o), o = null, null
			}
			var o = document.createElement("canvas"),
				a = {
					id: "exclamationSign-" + e,
					ctx: o.getContext("2d")
				};
			return o.id = a.id, o.style.zIndex = 1001, t.appendChild(o), i(), {
				destroy: r
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		"use strict";

		function e(e, t, n, i) {
			function r() {
				var e = n();
				(t.style.width = e.width + "px") && (f.width = t.clientWidth), (t.style.height = e.height + "px") && (f.height = t.clientHeight);
				var r = E.ctx,
					a = f.width,
					p = {
						l175: o(.175 * a),
						l20: o(.2 * a),
						l30: o(.3 * a),
						l37: o(.37 * a),
						l50: o(.5 * a),
						l63: o(.63 * a),
						l825: o(.825 * a)
					};
				r.beginPath(), r.fillStyle = "rgba(255,255,255,1)", r.strokeStyle = "rgba(255,255,255,1)", r.lineJoin = "round", r.moveTo(p.l20, p.l37), r.lineTo(p.l20, p.l63), r.lineTo(p.l30, p.l63), r.lineTo(p.l50, p.l825), r.lineTo(p.l50, p.l175), r.lineTo(p.l30, p.l37), r.fill(), r.lineWidth = o(.1 * a), r.lineCap = "round", i && (r.strokeStyle = "rgba(255,255,255,0.2)"), s = c = f.width / 2, u = .3 * a, l = 1.6 * Math.PI, d = .4 * Math.PI, r.beginPath(), r.arc(s, c, u, l, d, h), r.stroke(), u = .15 * a, l = 1.7 * Math.PI, d = .3 * Math.PI, r.beginPath(), r.arc(s, c, u, l, d, h), r.stroke()
			}

			function o(e) {
				return Math.round(e)
			}

			function a() {
				return E.ctx && E.ctx.clearRect(0, 0, f.width, f.height), f.parentElement === t && t.removeChild(f), f = null, null
			}
			var s, c, u, l, d, f = document.createElement("canvas"),
				h = !1,
				E = {
					id: "audio-" + e,
					ctx: f.getContext("2d")
				};
			return f.id = E.id, f.style.zIndex = 1001, t.appendChild(f), r(), {
				destroy: a
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(27), n(37)], r = function (e, t) {
		"use strict";

		function n(n, i, r, o) {
			function a() {
				return l = document.getElementById(n), {
					width: l.clientWidth,
					height: l.clientHeight
				}
			}

			function s(t) {
				if (i) {
					t = "undefined" == typeof t ? 0 : t;
					var n = a(),
						s = ["100%", "100%"],
						c = n.width / n.height,
						u = (c - d).toFixed(2),
						f = 1,
						h = 1;
					switch (o) {
						case "letterbox":
							t % 180 && (f = (f / d).toFixed(2), h = (h / d).toFixed(2));
							break;
						case "crop":
							u < 0 ? (h = (n.height / (n.width / d)).toFixed(2), f = h) : u >= 0 && (f = (n.width / (n.height * d)).toFixed(2), h = f), t % 180 && (f = (f * u).toFixed(2), h = (h * u).toFixed(2));
							break;
						case "fill":
							u < 0 ? h = (n.height / (n.width / d)).toFixed(2) : u >= 0 && (f = (n.width / (n.height * d)).toFixed(2));
							break;
						case "original":
							s[0] = r.width + "px", s[1] = r.height + "px";
							break;
						case "resize":
							"fixed" !== l.style.position && (s[0] = r.width + "px", s[1] = r.height + "px", l.style.width = s[0], l.style.height = s[1])
					}
					e.normalize(i, "transform", "translate(-50%, -50%) scaleX(" + f + ") scaleY(" + h + ") rotate(" + t + "deg)"), i.style.width = s[0], i.style.height = s[1]
				}
			}

			function c(e, t) {
				o = e || o, s(t)
			}

			function u() {
				return i = null, l = null, null
			}
			o = o || t.LETTERBOX;
			var l = document.getElementById(n),
				d = r.width / r.height;
			return s(), {
				update: c,
				destroy: u
			}
		}
		return {
			create: n
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			NONE: "none",
			LETTERBOX: "letterbox",
			CROP: "crop",
			FILL: "fill"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(39), n(42)], r = function (e, t) {
		"use strict";

		function n(n, r) {
			return i() ? e.create(n, r) : t.create(n, r)
		}

		function i() {
			return document.exitFullscreen || document.mozCancelFullScreen || document.webkitExitFullscreen || document.msExitFullscreen
		}
		return {
			create: n
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(40), n(41), n(21)], r = function (e, t, n) {
		"use strict";

		function i(i, r) {
			function o() {
				for (var e in t) t.hasOwnProperty(e) && (document.addEventListener(t[e], s), document.addEventListener(t[e].toLowerCase(), s))
			}

			function a() {
				for (var e in t) t.hasOwnProperty(e) && (document.removeEventListener(t[e], s), document.removeEventListener(t[e].toLowerCase(), s))
			}

			function s(t) {
				c() ? (h.style.width = "100vw", h.style.height = "100vh", E = e.ENTERED, r.emit(n.FULLSCREEN_ENTER)) : (E = e.EXITED, r.emit(n.FULLSCREEN_EXIT), h.style.cssText = p)
			}

			function c() {
				var e = u();
				return e === h
			}

			function u() {
				return document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || document.msFullscreenElement
			}

			function l() {
				try {
					c() ? document.exitFullScreen && document.exitFullScreen() : h.requestFullScreen && h.requestFullScreen()
				} catch (e) {}
			}

			function d() {
				l()
			}

			function f() {
				return a(), c() && (document.exitFullScreen && document.exitFullScreen(), E = e.EXITED, r.emit(n.FULLSCREEN_EXIT)), h = null, null
			}
			var h = document.getElementById(i),
				E = e.EXITED;
			h.requestFullScreen = h.requestFullscreen || h.mozRequestFullScreen || h.webkitRequestFullscreen || h.msRequestFullscreen, document.exitFullScreen = document.exitFullscreen || document.mozCancelFullScreen || document.webkitExitFullscreen || document.msExitFullscreen;
			var p = h.style.cssText;
			return o(), {
				change: d,
				destroy: f
			}
		}
		return {
			create: i
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			ENTERED: "entered",
			EXITED: "exited"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			FULLSCREEN_CHANGE: "fullscreenChange",
			WEBKIT_FULLSCREEN_CHANGE: "webkitfullscreenchange",
			MOZ_FULLSCREEN_CHANGE: "mozfullscreenchange",
			MS_FULLSCREEN_CHANGE: "MSFullscreenChange"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(27), n(40), n(21)], r = function (e, t, n) {
		"use strict";

		function i(e, i) {
			function r() {
				var e, t = document.querySelectorAll("meta");
				for (e = 0; e < t.length; ++e) {
					var n = t[e].getAttribute("name");
					n && "viewport" === n.toLowerCase() && (p = !0)
				}
			}

			function o() {
				return E === t.ENTERED
			}

			function a() {
				try {
					if (o()) {
						clearInterval(m);
						for (var e = 0; e < _.length; e += 1) _[e].element.style.cssText = _[e].cssText, _[e].border && (_[e].element.frameBorder = _[e].border);
						for (; _.length;) _.pop();
						v = g = null, E = t.EXITED, i.emit(n.FULLSCREEN_EXIT)
					} else {
						_.unshift({
							window: window,
							document: window.document,
							element: h,
							cssText: h.style.cssText,
							border: null
						}), d(window), m = setInterval(u, 300);
						for (var e = 0; e < _.length; e += 1) _[e].element.style.position = "fixed", _[e].element.style.left = 0, _[e].element.style.top = 0, _[e].border && (_[e].element.frameBorder = "0");
						s(), E = t.ENTERED, i.emit(n.FULLSCREEN_ENTER)
					}
				} catch (e) {}
			}

			function s() {
				_.forEach(c)
			}

			function c(e) {
				!g && (g = _[0]), T = g.document.documentElement.clientWidth / g.window.innerWidth, y = (0 === g.window.orientation ? g.window.screen.height : g.window.screen.width) - g.window.innerWidth * T, y = y > 1 && !p ? y : 0, e.element.style.width = g.window.innerWidth * T + "px", e.element.style.height = g.window.innerHeight * T - y + "px"
			}

			function u() {
				try {
					o() && s()
				} catch (e) {}
			}

			function l() {
				a()
			}

			function d(e) {
				if (e.parent != e.self)
					for (var t = e.parent, n = t.frames, i = 0; i < n.length; i++) {
						var r = n[i];
						if (e.self === r && r.frameElement && r.frameElement.tagName && "IFRAME" === r.frameElement.tagName) {
							for (var o = r.frameElement.attributes, a = !1, s = 0; s < o.length; s += 1)
								if (o[s].nodeName && o[s].nodeName.indexOf("allowfullscreen") !== -1 && "false" !== o[s].nodeValue) {
									a = !0;
									break
								} a && (_.unshift({
								window: e.parent,
								document: e.parent.document,
								element: r.frameElement,
								cssText: r.frameElement.style.cssText,
								border: r.frameElement.frameBorder
							}), d(e.parent))
						}
					}
			}

			function f() {
				return o() && a(), h = null, null
			}
			var h = document.getElementById(e),
				E = t.EXITED,
				p = !1,
				m = 0,
				_ = [],
				T = 1,
				y = 0,
				v = null,
				g = null;
			return r(), {
				change: l,
				destroy: f
			}
		}
		return {
			create: i
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(20), n(45), n(46), n(40), n(44), n(47), n(48), n(49), n(50)], r = function (e, t, n, i, r, o, a, s, c, u) {
		"use strict";

		function l(l, d, f) {
			function h() {
				P && (P.style.visibility = "visible")
			}

			function E() {
				P && (P.style.visibility = "hidden")
			}

			function p() {
				document.getElementById(b) || (P.id = b, P.style.width = "100%", P.style.height = (w ? "100" : Math.round(e.mobile ? 100 / 6 : 100 / 12)) + "%", P.style.maxHeight = C.clientWidth / 8 + "px", P.style.position = "absolute", P.style.left = "0", P.style.bottom = "0", P.style.zIndex = 2352346258378, P.style.backgroundColor = w ? "white" : "rgba(255,255,255,0.7)", !w && E(), C.appendChild(P), U.playPauseControl = o.create(P, d), U.timeControl = u.create(P, d), !w && L && (U.fullscreenControl = c.create(P, d)), U.volumeControl = s.create(P, d, w), U.muteControl = a.create(P, d, w), !w && C.addEventListener(t.MOUSE_MOVE, T, !0))
			}

			function m() {
				P.style.zIndex = 2352346258378
			}

			function _() {
				P.style.width = "100%";
				var t = Math.round(e.mobile ? C.clientHeight / 6 : C.clientHeight / 12),
					n = Math.round(e.mobile ? P.clientWidth / 8 : P.clientWidth / 12);
				P.style.height = (w ? "100" : Math.min(t, n)) + "px", P.style.maxHeight = C.clientWidth / 8 + "px";
				for (var i in U) U[i].update && U[i].update()
			}

			function T(e) {
				e.button && 0 !== e.button || e.target.id && e.target.id.indexOf("playButton-") !== -1 || y()
			}

			function y() {
				D && clearTimeout(D), D = 0, D = setTimeout(E, 5e3), h()
			}

			function v() {
				U.playPauseControl.update(n.PLAY)
			}

			function g() {
				U.playPauseControl.update(n.PAUSE)
			}

			function S(e) {
				!w && L && (m(), e ? U.fullscreenControl.update(r.ENTERED) : U.fullscreenControl.update(r.EXITED))
			}

			function A() {
				U.muteControl.update(i.MUTED)
			}

			function N() {
				U.muteControl.update(i.UNMUTED)
			}

			function I(e) {
				U.volumeControl.update(e)
			}

			function R(e) {
				U.timeControl.update(e)
			}

			function O() {
				D && clearTimeout(D);
				for (var e in U) "undefined" != typeof U[e] && U[e].destroy(), delete U[e];
				return w && C.removeEventListener(t.MOUSE_MOVE, T), P && P.parentElement === C && C.removeChild(P), P = null, C = null, null
			}
			var C = document.getElementById(l),
				b = "controls-" + l,
				P = document.createElement("div"),
				D = 0,
				w = f.style.audioPlayer,
				L = f.style.fullScreenControl,
				U = {
					playPauseControl: void 0,
					muteControl: void 0,
					volumeControl: void 0,
					fullscreenControl: void 0,
					timeControl: void 0
				};
			return p(), {
				size: _,
				appear: y,
				show: h,
				hide: E,
				play: v,
				pause: g,
				fullscreen: S,
				mute: A,
				unmute: N,
				volume: I,
				time: R,
				destroy: O
			}
		}
		return {
			create: l
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(20), n(23), n(45)], r = function (e, t, n, i) {
		"use strict";

		function r(r, o) {
			function a() {
				h.ctx && h.ctx.clearRect(0, 0, d.width, d.height), d.width = d.height = r.clientHeight;
				var e = d.width,
					t = {
						l20: s(.2 * e),
						l30: s(.3 * e),
						l45: s(.45 * e),
						l50: s(.5 * e),
						l55: s(.55 * e),
						l70: s(.7 * e),
						l75: s(.75 * e),
						l80: s(.8 * e)
					};
				f === i.PLAY ? (h.ctx.beginPath(), h.ctx.fillStyle = "rgba(0,0,0,1)", h.ctx.moveTo(t.l30, t.l20), h.ctx.lineTo(t.l30, t.l80), h.ctx.lineTo(t.l75, t.l50), h.ctx.fill()) : f === i.PAUSE && (h.ctx.beginPath(), h.ctx.fillStyle = "rgba(0,0,0,1)", h.ctx.moveTo(t.l30, t.l20), h.ctx.lineTo(t.l30, t.l80), h.ctx.lineTo(t.l45, t.l80), h.ctx.lineTo(t.l45, t.l20), h.ctx.moveTo(t.l55, t.l20), h.ctx.lineTo(t.l55, t.l80), h.ctx.lineTo(t.l70, t.l80), h.ctx.lineTo(t.l70, t.l20), h.ctx.fill())
			}

			function s(e) {
				return Math.floor(e)
			}

			function c(e) {
				e && (f = e), a()
			}

			function u() {
				h.ctx && h.ctx.clearRect(0, 0, d.width, d.height), d && d.parentElement === r && r.removeChild(d), d = null
			}

			function l(e) {
				f === i.PLAY ? o.emit(n.PLAY) : f === i.PAUSE && o.emit(n.PAUSE)
			}
			var d = document.createElement("canvas"),
				f = i.PLAY,
				h = {
					id: "playpause-" + r.id,
					ctx: d.getContext("2d")
				};
			return d.id = h.id, d.style.float = "left", d.style.display = "inline-block", d.addEventListener(e.mobile ? t.TOUCH_END : t.MOUSE_UP, l), r.appendChild(d), a(), {
				update: c,
				destroy: u
			}
		}
		return {
			create: r
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			PLAY: "play",
			PAUSE: "pause"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			MUTED: "muted",
			UNMUTED: "unmuted"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(20), n(23), n(46)], r = function (e, t, n, i) {
		"use strict";

		function r(r, o, a) {
			function s() {
				v.ctx && v.ctx.clearRect(0, 0, _.width, _.height), _.width = _.height = r.clientHeight;
				var e = _.width,
					t = {
						l175: c(.175 * e),
						l20: c(.2 * e),
						l30: c(.3 * e),
						l37: c(.37 * e),
						l50: c(.5 * e),
						l63: c(.63 * e),
						l825: c(.825 * e)
					};
				v.ctx.beginPath(), v.ctx.fillStyle = "rgba(0,0,0,1)", v.ctx.strokeStyle = "rgba(0,0,0,1)", v.ctx.lineJoin = "round", v.ctx.moveTo(t.l20, t.l37), v.ctx.lineTo(t.l20, t.l63), v.ctx.lineTo(t.l30, t.l63), v.ctx.lineTo(t.l50, t.l825), v.ctx.lineTo(t.l50, t.l175), v.ctx.lineTo(t.l30, t.l37), v.ctx.fill(), v.ctx.lineWidth = c(.1 * e), v.ctx.lineCap = "round", T === i.MUTED && (v.ctx.strokeStyle = "rgba(0,0,0,0.2)"), f = h = _.width / 2, E = .3 * e, p = 1.6 * Math.PI, m = .4 * Math.PI, v.ctx.beginPath(), v.ctx.arc(f, h, E, p, m, y), v.ctx.stroke(), E = .15 * e, p = 1.7 * Math.PI, m = .3 * Math.PI, v.ctx.beginPath(), v.ctx.arc(f, h, E, p, m, y), v.ctx.stroke()
			}

			function c(e) {
				return Math.round(e)
			}

			function u(e) {
				e && (T = e), s()
			}

			function l() {
				v.ctx && v.ctx.clearRect(0, 0, _.width, _.height), _ && _.parentElement === r && r.removeChild(_), _ = null
			}

			function d(e) {
				T === i.MUTED ? o.emit(n.UNMUTE) : T === i.UNMUTED && o.emit(n.MUTE)
			}
			var f, h, E, p, m, _ = document.createElement("canvas"),
				T = i.UNMUTED,
				y = !1,
				v = {
					id: "mute-" + r.id,
					ctx: _.getContext("2d")
				};
			return _.id = v.id, _.style.float = "right", _.style.display = "inline-block", _.addEventListener(e.mobile ? t.TOUCH_END : t.MOUSE_UP, d), r.appendChild(_), s(), {
				update: u,
				destroy: l
			}
		}
		return {
			create: r
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(20), n(23), n(46)], r = function (e, t, n, i) {
		"use strict";

		function r(i, r, o) {
			function a() {
				S.ctx && S.ctx.clearRect(0, 0, y.width, y.height), y.height = i.clientHeight, y.width = 3 * y.height, d = y.width, f = y.height, S.ctx.beginPath(), S.ctx.strokeStyle = "rgba(0,0,0,1)", S.ctx.lineJoin = "round", h = s(d * v), E = s(f * (1 - g)), p = s(d * (1 - v)), m = s(f * g), S.ctx.moveTo(h, E), S.ctx.lineTo(p, E), S.ctx.lineTo(p, m), S.ctx.fillStyle = "rgba(0,0,0,0.2)", S.ctx.fill(), S.value && (S.ctx.closePath(), S.ctx.beginPath(), _ = s(h + S.value * (p - h)), T = s(E - S.value * (E - m)), S.ctx.moveTo(h, E), S.ctx.lineTo(_, E), S.ctx.lineTo(_, T), S.ctx.fillStyle = "rgba(0,0,0,1)", S.ctx.fill())
			}

			function s(e) {
				return Math.round(e)
			}

			function c(e) {
				e && (S.value = e), a()
			}

			function u() {
				S.ctx && S.ctx.clearRect(0, 0, y.width, y.height), y && y.parentElement === i && i.removeChild(y), y = null
			}

			function l(e) {
				var t = e.target || e.srcElement,
					i = t.currentStyle || window.getComputedStyle(t, null),
					o = isNaN(parseInt(i.borderLeftWidth)) ? 0 : parseInt(i.borderLeftWidth, 10),
					a = isNaN(parseInt(i.borderTopWidth)) ? 0 : parseInt(i.borderTopWidth, 10),
					s = t.getBoundingClientRect(),
					c = e.changedTouches && e.changedTouches.length ? e.changedTouches[0] : e.touches && e.touches.length ? e.touches[0] : {
						clientX: s.left,
						clientY: s.top
					},
					u = (e.clientX ? e.clientX : c.clientX) - o - s.left,
					l = ((e.clientY ? e.clientY : c.clientY) - a - s.top, 0);
				u > h && u < p ? l = (u - h) / (p - h) : u > p && (l = 1);
				var d = Math.round(100 * l) / 100;
				r.emit(n.VOLUME_CHANGE, {
					volume: d
				})
			}
			var d, f, h, E, p, m, _, T, y = document.createElement("canvas"),
				v = .1,
				g = .15,
				S = {
					id: "volume-" + i.id,
					ctx: y.getContext("2d"),
					value: 1
				};
			return y.id = S.id, y.style.float = "right", y.style.display = "inline-block", y.addEventListener(e.mobile ? t.TOUCH_END : t.MOUSE_UP, l), i.appendChild(y), a(), {
				update: c,
				destroy: u
			}
		}
		return {
			create: r
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(20), n(23), n(40)], r = function (e, t, n, i) {
		"use strict";

		function r(r, o) {
			function a() {
				E.ctx && E.ctx.clearRect(0, 0, f.width, f.height), f.width = f.height = r.clientHeight;
				var e = f.width,
					t = {
						l20: s(.2 * e),
						l35: s(.35 * e),
						l65: s(.65 * e),
						l80: s(.8 * e)
					};
				E.ctx.beginPath(), E.ctx.strokeStyle = "rgba(0,0,0,1)", E.ctx.lineCap = "round", E.ctx.lineWidth = c(.1 * e), h === i.ENTERED ? (E.ctx.beginPath(), E.ctx.moveTo(t.l20, t.l35), E.ctx.lineTo(t.l35, t.l35), E.ctx.lineTo(t.l35, t.l20), E.ctx.stroke(), E.ctx.beginPath(), E.ctx.moveTo(t.l65, t.l20), E.ctx.lineTo(t.l65, t.l35), E.ctx.lineTo(t.l80, t.l35), E.ctx.stroke(), E.ctx.beginPath(), E.ctx.moveTo(t.l80, t.l65), E.ctx.lineTo(t.l65, t.l65), E.ctx.lineTo(t.l65, t.l80), E.ctx.stroke(), E.ctx.beginPath(), E.ctx.moveTo(t.l35, t.l80), E.ctx.lineTo(t.l35, t.l65), E.ctx.lineTo(t.l20, t.l65), E.ctx.stroke()) : h === i.EXITED && (E.ctx.beginPath(), E.ctx.moveTo(t.l20, t.l35), E.ctx.lineTo(t.l20, t.l20), E.ctx.lineTo(t.l35, t.l20), E.ctx.stroke(), E.ctx.beginPath(), E.ctx.moveTo(t.l65, t.l20), E.ctx.lineTo(t.l80, t.l20), E.ctx.lineTo(t.l80, t.l35), E.ctx.stroke(), E.ctx.beginPath(), E.ctx.moveTo(t.l80, t.l65), E.ctx.lineTo(t.l80, t.l80), E.ctx.lineTo(t.l65, t.l80), E.ctx.stroke(), E.ctx.beginPath(), E.ctx.moveTo(t.l35, t.l80), E.ctx.lineTo(t.l20, t.l80), E.ctx.lineTo(t.l20, t.l65), E.ctx.stroke(), E.ctx.stroke())
			}

			function s(e) {
				return Math.floor(e) + .5
			}

			function c(e) {
				return Math.round(e)
			}

			function u(e) {
				e && (h = e), a()
			}

			function l() {
				E.ctx && E.ctx.clearRect(0, 0, f.width, f.height), f && f.parentElement === r && r.removeChild(f), f = null
			}

			function d(e) {
				h === i.EXITED ? o.emit(n.FULLSCREEN_ENTER) : h === i.ENTERED && o.emit(n.FULLSCREEN_EXIT)
			}
			var f = document.createElement("canvas"),
				h = i.EXITED,
				E = {
					id: "fullscreen-" + r.id,
					ctx: f.getContext("2d")
				};
			return f.id = E.id, f.style.float = "right", f.style.display = "inline-block", f.addEventListener(e.mobile ? t.TOUCH_END : t.MOUSE_UP, d), r.appendChild(f), a(), {
				update: u,
				destroy: l
			}
		}
		return {
			create: r
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		"use strict";

		function e(e, t) {
			function n() {
				u.ctx && u.ctx.clearRect(0, 0, s.width, s.height);
				var t = s.height = e.clientHeight,
					n = s.width = 3 * s.height;
				u.ctx.textAlign = "left", u.ctx.textBaseline = "middle", u.ctx.font = "bold " + i(.6 * t) + 'px "Arial"', u.ctx.fillStyle = "rgba(0,0,0,1)";
				try {
					u.ctx.fillText(c, i(.1 * n), i(.5 * t))
				} catch (e) {}
			}

			function i(e) {
				return Math.round(e)
			}

			function r(e) {
				var t = Math.floor(e / 3600),
					n = Math.floor((e - 3600 * t) / 60),
					e = Math.floor(e - 3600 * t - 60 * n),
					i = "";
				return 0 != t && (i = t + ":"), n = n < 10 && "" !== i ? "0" + n : String(n), i += n + ":", i += e < 10 ? "0" + e : String(e)
			}

			function o(e) {
				e && (c = r(e)), n()
			}

			function a() {
				u.ctx && u.ctx.clearRect(0, 0, s.width, s.height), s && s.parentElement === e && e.removeChild(s), s = null
			}
			var s = document.createElement("canvas"),
				c = "0:00",
				u = {
					id: "time-" + e.id,
					ctx: s.getContext("2d")
				};
			return s.id = u.id, s.style.float = "left", s.style.display = "inline-block", e.appendChild(s), n(), {
				update: o,
				destroy: a
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(3), n(13), n(14), n(53), n(54), n(55), n(56), n(52), n(57), n(58), n(59), n(60), n(61), n(62)], r = function (e, t, n, i, r, o, a, s, c, u, l, d, f, h, E) {
		function p() {
			function s(e, t) {
				console.log("NanoMetrics api version " + this.version);
				try {
					y(), P.debug("setup"), g = t, x = e;
					var n = x.metrics;
					if (!n) throw new w(o.METRICS_NO_OBJECT, r.SETUP.METRICS_NO_OBJECT);
					if ("object" != typeof n) throw new w(o.METRICS_NOT_OBJECT, r.SETUP.METRICS_NOT_OBJECT);
					if (!Object.getOwnPropertyNames(n).length) throw new w(o.METRICS_EMPTY_OBJECT, r.SETUP.METRICS_EMPTY_OBJECT);
					for (var a in n)
						if (!n.hasOwnProperty(a) || !C.hasOwnProperty(a) || (C[a] = n[a], 0 === a.indexOf("customField"))) {
							var s, m = a.split("customField");
							if (!(m.length > 1 && 0 === m[0].length)) throw new w(o.METRICS_PROPERTY_INVALID.replace("$key$", a), r.SETUP.METRICS_PROPERTY_INVALID);
							if (s = parseInt(m[1]), isNaN(s)) throw new w(o.METRICS_INDEX_INVALID.replace("$key$", a), r.SETUP.METRICS_INDEX_INVALID);
							if (s.toString() != m[1]) throw new w(o.METRICS_INDEX_INCORRECT.replace("$key$", a), r.SETUP.METRICS_INDEX_INCORRECT);
							if (s < 1 || s > 10) throw new w(o.METRICS_INDEX_RANGE.replace("$key$", a), r.SETUP.METRICS_INDEX_RANGE);
							b["application_custom_field_" + m[1]] = n[a]
						} if (!C.accountId || !C.accountKey) throw new w(o.METRICS_NO_CREDENTIALS, r.SETUP.METRICS_NO_CREDENTIALS);
					for (var a in n) P.debug("metrics config[" + a + "]: " + JSON.stringify(n[a]));
					S = u.create(), A = l.create(), N = d.create(C.statsInterval), I = f.create(), R = h.create(), O = E.create(), v = c.create({
						accountId: C.accountId,
						accountKey: C.accountKey
					});
					for (var _ in i) i.hasOwnProperty(_) && g.on(i[_], p)
				} catch (e) {
					throw e
				}
			}

			function p(e) {
				try {
					var t = m(e);
					switch (e.name) {
						case i.READY:
							T(t);
							break;
						case i.LOADING:
							I.reset(), N.reset(), T(t);
							break;
						case i.STREAM_INFO:
						case i.STREAM_INFO_UPDATE:
							L = e.data.streamInfo, _(S, "measure", e, t), T(t);
							break;
						case i.PLAY:
							M = e.data.stats, _(A, "measure", e, t), T(t);
							break;
						case i.STATS:
							U = e.data.stats, _(N, "measure", e, t) && T(t);
							break;
						case i.START_BUFFERING:
							I.start();
							break;
						case i.STOP_BUFFERING:
							I.end(), _(I, "measure", e, t) && (t.message = t.player_event_name = "BUFFERING", T(t));
							break;
						case i.PAUSE:
							_(R, "measure", e, t), _(I, "reset", e, t), T(t), L = {}, U = {}, M = {}, F = 0;
							break;
						case i.ERROR:
							_(O, "measure", e, t), T(t);
							break;
						case i.DESTROY:
							T(t);
							break;
						case i.METADATA:
							break;
						case i.PLAYBACK_FINISHED:
							break;
						case i.MEDIA:
							break;
						case i.MUTE:
							break;
						case i.UNMUTE:
							break;
						case i.VOLUME_CHANGE:
							break;
						case i.STATE_CHANGE:
							break;
						case i.WARNING:
					}
				} catch (e) {
					P.debug("metrics event error: " + (e.message ? e.message : "exception"))
				}
			}

			function m(e) {
				var t = x && x.source && x.source.h5live ? x.source.h5live : {},
					n = t.server || {
						websocket: "",
						hls: "",
						progressive: ""
					},
					r = t.rtmp && t.rtmp.url ? t.rtmp.url : "",
					o = t.rtmp && t.rtmp.streamname ? t.rtmp.streamname : "",
					a = {
						application_name: "h5live-player",
						application_user_id: "" + C.userId,
						application_event_id: "" + C.eventId,
						application_account_id: "" + C.accountId,
						application_account_key: "" + C.accountKey,
						message: e.name.replace(/\b\w/g, function (e) {
							return e.toLowerCase()
						}).replace(/([A-Z])/g, "_$1").toUpperCase(),
						severity: "info",
						client_ip: "",
						timestamp_client: (new Date).toISOString(),
						origin: document.location.origin,
						path_name: document.location.pathname,
						referrer: document.location.href,
						search: document.location.search,
						os: D.os,
						os_version: D.osVersion,
						browser: D.browser,
						browser_version: D.browserVersion,
						player_id: g.id,
						player_type: g.type,
						player_version: g.version,
						player_event_name: e.name.replace(/\b\w/g, function (e) {
							return e.toLowerCase()
						}).replace(/([A-Z])/g, "_$1").toUpperCase(),
						server_domain: n.websocket && n.websocket.length ? n.websocket.split("//")[1].split("/")[0] : "",
						rtmp_url: r,
						rtmp_streamname: o,
						connection_id: L && L.url ? L.url.split("&cid=")[1].split("&pid=")[0] : "",
						time_played: U.currentTime ? Math.round(1e3 * U.currentTime) / 1e3 : 0,
						time_elapsed: e.name === i.PLAY && (F = performance.now()) || 0 === U.currentTime ? 0 : Math.round(performance.now() - F) / 1e3,
						bintu_orga_hash: r.indexOf("bintu") !== -1 || n.websocket && n.websocket.length && n.websocket.indexOf("bintu") !== -1 && r.indexOf("localhost") !== -1 ? o.split("-")[0] : ""
					};
				for (var s in b) b.hasOwnProperty(s) && (a[s] = "" + b[s]);
				return a
			}

			function _(e, t, n, i) {
				var r = e[t](n, i);
				if (r) {
					for (var o in r) r.hasOwnProperty(o) && (i[o] = r[o]);
					return !0
				}
				return !1
			}

			function T(e) {
				P.debug("send " + e.player_event_name), v.send(e.player_event_name, e)
			}

			function y() {
				if ("object" == typeof g && "function" == typeof g.off)
					for (var e in i) i.hasOwnProperty(e) && g.off(i[e], p);
				v && (v.destroy(), v = null), S = null, A = null, N = null, I = null, R = null, O = null
			}
			var v, g, S, A, N, I, R, O, C = a.create().metrics,
				b = {},
				P = n.create("NanoMetrics"),
				D = e,
				w = t,
				L = {},
				U = {},
				M = {},
				x = {},
				F = 0;
			return {
				setup: s,
				destroy: y
			}
		}
		return p.emptyConfig = a, p.validConfig = s, p
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13)], r = function (e) {
		function t(t) {
			function n(e, t) {
				try {
					s += 1;
					var n = new XMLHttpRequest;
					c[s] = n;
					var r = c[s];
					if (r.open("POST", l, f), r.onreadystatechange = function (e, t) {
							if (this && 4 === this.readyState) {
								var n = this.status >= 400 ? "fail" : this.status >= 200 ? "success" : "unknown";
								i.call(null, e, t, n)
							}
						}.bind(r, s, e), ["abort", "error", "timeout"].forEach(function (t) {
							r.addEventListener(t, i.bind(null, s, e, t))
						}), "undefined" != typeof t) {
						if ("string" == typeof t) try {
							t = JSON.parse(t)
						} catch (e) {
							t = null
						}
						if ("object" == typeof t) try {
							t = JSON.stringify(t), d.debug("#" + s + " " + e + " send " + t), setTimeout(i.bind(null, s, e, "suspend"), 5e3), r.send(t)
						} catch (e) {
							i.call(null, s, "catch " + e.message)
						}
					} else r.send()
				} catch (e) {
					i.call(null, s, "catch " + e.message)
				}
			}

			function i(e, t, n) {
				"undefined" != typeof c[e] && (d.debug("#" + e + " " + t + " " + n + " " + c[e].status), delete c[e])
			}

			function r() {
				for (var e in c) c.hasOwnProperty(e) && delete c[e]
			}
			var o = {
				accountId: void 0,
				accountKey: void 0
			};
			for (var a in o) o.hasOwnProperty(a) && t && t.hasOwnProperty(a) && void 0 !== t[a] && (o[a] = t[a]);
			var s = 0,
				c = {},
				u = "https://glog1.nanocosmos.de/gelf/",
				l = u + o.accountKey,
				d = e.create("MetricsLogger"),
				f = !0;
			return window.addEventListener("unload", function () {
				f = !1
			}), {
				send: n,
				destroy: r
			}
		}
		return {
			create: t
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			SETUP: {
				METRICS_NO_OBJECT: 5201,
				METRICS_NOT_OBJECT: 5202,
				METRICS_EMPTY_OBJECT: 5203,
				METRICS_INDEX_INVALID: 5204,
				METRICS_INDEX_INCORRECT: 5205,
				METRICS_INDEX_RANGE: 5206,
				METRICS_PROPERTY_INVALID: 5207,
				METRICS_NO_CREDENTIALS: 5208
			}
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			METRICS_NO_OBJECT: "Metrics configuration error. No metrics config object passed.",
			METRICS_NOT_OBJECT: "Metrics configuration error. Metrics config is not from type 'object'.",
			METRICS_EMPTY_OBJECT: "Metrics configuration error. Metrics config is empty.",
			METRICS_INDEX_INVALID: "Metrics configuration error. The custom property '$key$' has no valid index number, the range is 1 to 10 e.g. 'customField1'.",
			METRICS_INDEX_INCORRECT: "Metrics configuration error. The custom property '$key$' is not indexed correctly, the range is 1 to 10 e.g. 'customField1'.",
			METRICS_INDEX_RANGE: "Metrics configuration error. The custom property '$key$' has an index out of range, the range is 1 to 10 e.g. 'customField1'.",
			METRICS_PROPERTY_INVALID: "Metrics configuration error. The property '$key$' is not valid.",
			METRICS_NO_CREDENTIALS: "Metrics configuration error. No credentials passed."
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			return {
				metrics: {
					accountId: void 0,
					accountKey: void 0,
					userId: "",
					eventId: "",
					statsInterval: 10,
					customField1: void 0,
					customField2: void 0,
					customField3: void 0,
					customField4: void 0,
					customField5: void 0,
					customField6: void 0,
					customField7: void 0,
					customField8: void 0,
					customField9: void 0,
					customField10: void 0
				}
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			return {
				metrics: {
					accountId: "string",
					accountKey: "string",
					userId: "string",
					eventId: "string",
					statsInterval: "number",
					customField1: "*",
					customField2: "*",
					customField3: "*",
					customField4: "*",
					customField5: "*",
					customField6: "*",
					customField7: "*",
					customField8: "*",
					customField9: "*",
					customField10: "*"
				}
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			function e(e, t) {
				var n = e.data.streamInfo;
				return {
					stream_info_has_audio: n.haveAudio,
					stream_info_has_video: n.haveVideo,
					stream_info_width: n.videoInfo ? n.videoInfo.width : 0,
					stream_info_height: n.videoInfo ? n.videoInfo.height : 0,
					stream_info_frame_rate: n.videoInfo ? n.videoInfo.frameRate : 0,
					stream_info_sample_rate: n.audioInfo ? n.audioInfo.sampleRate : 0,
					stream_info_channels: n.audioInfo ? n.audioInfo.channels : 0,
					stream_info_bits_per_sample: n.audioInfo ? n.audioInfo.bitsPerSample : 0,
					connection_id: n.url.split("&cid=")[1].split("&pid=")[0]
				}
			}
			return {
				measure: e
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			function e(e, t) {
				var n, i = {
					connecting: 0,
					connected: 0,
					firstFragmentReceived: 0,
					firstFrameRendered: 0,
					playable: 0,
					playing: 0
				};
				try {
					if (e.data && e.data.stats && "object" == typeof e.data.stats) {
						var r = e.data.stats;
						for (n in i) r.hasOwnProperty(n) && "number" == typeof r[n] && (i[n] = r[n])
					}
				} catch (e) {}
				var o = {},
					a = [{
						start: "connecting",
						end: "connecting",
						measurement: "play_connecting",
						value: 0
					}, {
						start: "connecting",
						end: "connected",
						measurement: "play_connected",
						value: 0
					}, {
						start: "connected",
						end: "firstFragmentReceived",
						measurement: "play_first_fragment_received",
						value: 0
					}, {
						start: "firstFragmentReceived",
						end: "firstFrameRendered",
						measurement: "play_first_frame_rendered",
						value: 0
					}, {
						start: "firstFrameRendered",
						end: "playable",
						measurement: "play_playable",
						value: 0
					}, {
						start: "playable",
						end: "playing",
						measurement: "play_playing",
						value: 0
					}];
				return a.forEach(function (e, t) {
					var n = i[e.start],
						r = i[e.end];
					r - n >= 0 && (e.value = Math.floor(r - n)), o[e.measurement + "_relative"] = e.value, o[e.measurement + "_absolute"] = i[e.end]
				}), o.play_start_time_total = o.play_playing_absolute, o
			}
			return {
				measure: e
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e) {
			function t(e, t) {
				var n = {
					currentTime: 0,
					playout: {
						start: 0,
						end: 0
					},
					buffer: {
						start: 0,
						end: 0,
						delay: {
							before: 0,
							current: 0,
							avg: 0,
							min: 0,
							max: 0
						},
						update: {
							current: 0,
							avg: 0,
							min: 0,
							max: 0
						}
					},
					quality: {
						droppedVideoFrames: 0,
						droppedVideoFramesCurrent: 0,
						corruptedVideoFrames: 0,
						corruptedVideoFramesCurrent: 0,
						creationTime: 0,
						totalVideoFrames: 0
					},
					bitrate: {
						current: 0,
						avg: 0,
						min: 0,
						max: 0
					},
					framerate: {
						current: 0,
						avg: 0,
						min: 0,
						max: 0
					}
				};
				e.data.stats && "object" == typeof e.data.stats && (n = e.data.stats);
				try {
					for (a += 1, s.push(n.buffer.delay.current), c.push(n.bitrate.current), u.push(n.framerate.current); s.length > o;) s.shift();
					for (; c.length > o;) c.shift();
					for (; u.length > o;) u.shift();
					if (a % o === 0) {
						var i = JSON.parse(JSON.stringify(s)).sort(function (e, t) {
								return e - t
							}, 0),
							r = JSON.parse(JSON.stringify(c)).sort(function (e, t) {
								return e - t
							}, 0),
							d = JSON.parse(JSON.stringify(u)).sort(function (e, t) {
								return e - t
							}, 0);
						return l.stats_buffer_average = Math.round(i.reduce(function (e, t) {
							return e + t
						}, 0) / i.length * 1e3) / 1e3, l.stats_buffer_min = Math.round(1e3 * i[0]) / 1e3, l.stats_buffer_max = Math.round(1e3 * i[i.length - 1]) / 1e3, l.stats_bitrate_average = Math.round(r.reduce(function (e, t) {
							return e + t
						}, 0) / r.length / 1e3), l.stats_bitrate_min = Math.round(r[0] / 1e3), l.stats_bitrate_max = Math.round(r[i.length - 1] / 1e3), l.stats_frame_rate_average = Math.round(d.reduce(function (e, t) {
							return e + t
						}, 0) / d.length * 1e3) / 1e3, l.stats_frame_rate_min = d[0], l.stats_frame_rate_max = d[d.length - 1], l.stats_measure_count += 1, l.stats_quality_dropped_video_frames = n.quality.droppedVideoFrames, l.stats_quality_dropped_video_frames_current = n.quality.droppedVideoFramesCurrent, l.stats_quality_corrupted_video_frames = n.quality.corruptedVideoFrames, l.stats_quality_corrupted_video_frames_current = n.quality.corruptedVideoFramesCurrent, l.stats_quality_creation_time = n.quality.creationTime, l.stats_quality_total_video_frames = n.quality.totalVideoFrames, l
					}
					return !1
				} catch (e) {
					return !1
				}
			}

			function n() {
				for (a = 0; s.length;) s.shift();
				for (; c.length;) c.shift();
				for (; u.length;) u.shift();
				for (var e in l) "stats_measure_samples" !== e && (l[e] = 0)
			}
			var i = 10;
			e && !isNaN(e) && e >= 1 && (i = e);
			var r = 10,
				o = r * i,
				a = 0,
				s = [],
				c = [],
				u = [],
				l = {
					stats_buffer_average: 0,
					stats_buffer_min: 0,
					stats_buffer_max: 0,
					stats_bitrate_average: 0,
					stats_bitrate_min: 0,
					stats_bitrate_max: 0,
					stats_frame_rate_average: 0,
					stats_frame_rate_min: 0,
					stats_frame_rate_max: 0,
					stats_quality_dropped_video_frames: 0,
					stats_quality_dropped_video_frames_current: 0,
					stats_quality_corrupted_video_frames: 0,
					stats_quality_corrupted_video_frames_current: 0,
					stats_quality_creation_time: 0,
					stats_quality_total_video_frames: 0,
					stats_measure_count: 0,
					stats_measure_samples: o
				};
			return {
				measure: t,
				reset: n
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			function e() {
				o = performance.now()
			}

			function t() {
				a = performance.now()
			}

			function n(e, t) {
				if (a) {
					var n = Math.round(Math.abs(a - o)) / 1e3;
					if (a = o = 0, n >= r) {
						s.push(n), c.buffering_duration_current = n, c.buffering_duration_total += n, c.buffering_count += 1, c.buffering_duration_average = Math.round(c.buffering_duration_total / c.buffering_count * 1e3) / 1e3;
						for (var i in c) !/buffering_count/.test(i) && c[i];
						return c
					}
				}
				return !1
			}

			function i(e, t) {
				if (e && t) {
					for (a = o = 0; s.length;) s.pop();
					var n = {};
					for (var i in c) !/buffering_duration_current/.test(i) && (n[i] = c[i]), c[i] = 0;
					return n.time_played_total = t.time_played, n.time_elapsed_total = t.time_elapsed, n.time_buffering_total = n.buffering_duration_total, n
				}
			}
			var r = 1,
				o = 0,
				a = 0,
				s = [],
				c = {
					buffering_duration_current: 0,
					buffering_duration_average: 0,
					buffering_duration_total: 0,
					buffering_count: 0
				};
			return {
				start: e,
				end: t,
				measure: n,
				reset: i
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			function e(e, t) {
				return {
					pause_reason: e.data.reason
				}
			}
			return {
				measure: e
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			function e(e, t) {
				return {
					error_code: e.data.code,
					error_message: e.data.message
				}
			}
			return {
				measure: e
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(3), n(64), n(65), n(66), n(67), n(68), n(69)], r = function (e, t, n, i, r, o, a) {
		"use strict";

		function s(i) {
			var r;
			return new Promise(function (a, s) {
				function c(i) {
					"onstatuserror" === i.error ? s(new e(n.BINTU_STATUS_ERROR.replace("$status$", i.request.status), t.SETUP.BINTU_STATUS_ERROR)) : i.error ? s(new e(i.error)) : i.message && s(new e(i.message))
				}
				if (i && i.source && i.source.bintu) {
					if (i.source.bintu.apiurl || (i.source.bintu.apiurl = "https://bintu.nanocosmos.de"), !i.source.bintu.streamid) return void s(new e(n.BINTU_NO_STREAM_ID, t.SETUP.BINTU_NO_STREAM_ID));
					r = new o(i.source.bintu.apiurl, null, null, "player"), r.getStream(i.source.bintu.streamid, function (r) {
						try {
							var o = JSON.parse(r.responseText),
								u = (o.id, o.state, o.playout),
								l = u.rtmp,
								d = u.hls,
								f = u.h5live;
							if (l && !l.length && !f || f && !f.length) s(new e(n.BINTU_STREAM_NOT_LIVE, t.SETUP.BINTU_STREAM_NOT_LIVE));
							else {
								if (d && d.length) {
									var h = d[0].url;
									i.source.hls = h
								}
								if (f && f.length) {
									if (i.source.h5live = i.source.h5live || {}, !i.source.h5live.server && (i.source.h5live.server = {}))
										for (var E in f[0].server) f[0].server.hasOwnProperty(E) && (i.source.h5live.server[E] = f[0].server[E].replace("bintu-stream", "bintu-h5live"));
									i.source.h5live.rtmp = {}, i.source.h5live.rtmp.url = f[0].rtmp.url, i.source.h5live.rtmp.streamname = f[0].rtmp.streamname
								} else l && l.length && (i.source.h5live = i.source.h5live || {}, i.source.h5live.rtmp = {}, i.source.h5live.rtmp.url = l[0].url, i.source.h5live.rtmp.streamname = l[0].streamname)
							}
							a(i)
						} catch (e) {
							c(e)
						}
					}, c)
				} else a(i)
			})
		}

		function c(e) {
			return s({
				source: e
			})
		}
		return {
			setup: s,
			updateSource: c,
			emptyConfig: i,
			validConfig: r
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			SETUP: {
				BINTU_STREAM_NOT_LIVE: 5101,
				BINTU_NO_STREAM_ID: 5102,
				BINTU_STATUS_ERROR: 5103
			}
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			BINTU_STREAM_NOT_LIVE: "Could not find bintu stream. The stream is not live.",
			BINTU_NO_STREAM_ID: "No bintu stream id passed.",
			BINTU_STATUS_ERROR: "The bintu service rejected with http status $status$."
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			return {
				source: {
					bintu: {
						apiurl: "",
						streamid: ""
					}
				}
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			return {
				source: {
					bintu: {
						apiurl: "string",
						streamid: "string"
					}
				}
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		"use strict";
		var e = function (e, t, n, i) {
				if (!(e.length > 0 && ("string" == typeof e || e instanceof String))) throw new Error("The param 'apiUrl' must be of type 'string' and also may not be empty string.");
				if (this.apiUrl = e, t && ("string" == typeof t || t instanceof String) && (this.apiKey = t), n && ("string" == typeof n || n instanceof String) && (this.playerKey = n), i && ("string" == typeof i || i instanceof String)) {
					if ("api" !== i && "player" !== i) throw new Error("The param 'keyMode' must be 'api', 'player' or undefined");
					this.keyMode = i
				}
			},
			t = e.prototype,
			n = function (e, t, n, i, r, o) {
				var a = this;
				a.method = e || "GET", a.url = t || "http://localhost:8088", a.header = n || {}, a.async = i || !0, a.onSuccess = r || function (e) {}, a.onError = o || function (e) {}, a.Send = function (e) {
					var t = new XMLHttpRequest;
					t.open(a.method, a.url, a.async);
					for (var n in a.header) t.setRequestHeader(n, a.header[n]);
					if (t.onreadystatechange = function () {
							4 === t.readyState && 200 === t.status ? "function" == typeof a.onSuccess && a.onSuccess(t) : 4 === t.readyState && 200 !== t.status && "function" == typeof a.onError && a.onError({
								error: "onstatuserror",
								request: t
							})
						}, t.onabort = function () {
							"function" == typeof a.onError && a.onError({
								error: "onabort",
								request: t
							})
						}, t.onerror = function () {
							"function" == typeof a.onError && a.onError({
								error: "onerror",
								request: t
							})
						}, t.ontimeout = function () {
							"function" == typeof a.onError && a.onError({
								error: "ontimeout",
								request: t
							})
						}, "undefined" != typeof e) {
						if ("string" == typeof e) try {
							e = JSON.parse(e)
						} catch (n) {
							e = null, "function" == typeof a.onError && a.onError({
								error: "invalid json string",
								request: t
							})
						}
						if ("object" == typeof e) try {
							e = JSON.stringify(e), t.send(e)
						} catch (e) {
							"function" == typeof a.onError && a.onError({
								error: "invalid json object",
								request: t
							})
						}
					} else t.send()
				}
			};
		return t.apiKey = null, t.apiUrl = null, t.playerKey = null, t.keyMode = "api", t.createStream = function (e, t, i) {
			if (!this.apiUrl) return "function" == typeof i && i({
				error: "no api url set",
				request: {
					responseText: "no response error"
				}
			});
			if (!this.apiKey) return "function" == typeof i && i({
				error: "no api key set",
				request: {
					responseText: "no response error"
				}
			});
			if ("api" !== this.keyMode) return "function" == typeof i && i({
				error: "wrong key mode set",
				request: {
					responseText: "no response error"
				}
			});
			var r = new n("POST", this.apiUrl + "/stream");
			r.header = {
				Accept: "application/json",
				"Content-Type": "application/json",
				"X-BINTU-APIKEY": this.apiKey
			}, r.onSuccess = t, r.onError = i, "object" == typeof e && "function" == typeof e.push && e.length > 0 && ("string" == typeof e[0] || e[0] instanceof String) ? r.Send({
				tags: e
			}) : r.Send()
		}, t.getStream = function (e, t, i) {
			if (!this.apiUrl) return "function" == typeof i && i({
				error: "no api url set",
				request: {
					responseText: "no response error"
				}
			});
			if (!this.apiKey && "api" === this.keyMode) return "function" == typeof i && i({
				error: "no api key set",
				request: {
					responseText: "no response error"
				}
			});
			var r = new n("GET", this.apiUrl + "/stream/" + e);
			r.header = "api" === this.keyMode && this.apiKey ? {
				Accept: "application/json",
				"Content-Type": "application/json",
				"X-BINTU-APIKEY": this.apiKey
			} : {
				Accept: "application/json",
				"Content-Type": "application/json; charset=utf-8"
			}, r.onSuccess = t, r.onError = i, r.Send()
		}, t.getStreams = function (e, t, i) {
			if (!this.apiUrl) return "function" == typeof i && i({
				error: "no api url set",
				request: {
					responseText: "no response error"
				}
			});
			if (!this.apiKey && "api" === this.keyMode) return "function" == typeof i && i({
				error: "no api key set",
				request: {
					responseText: "no response error"
				}
			});
			if (!this.playerKey && "player" === this.keyMode) return "function" == typeof i && i({
				error: "no player key set",
				request: {
					responseText: "no response error"
				}
			});
			var r = new n("GET", this.apiUrl + "/stream/" + streamId),
				o = this.apiUrl + "/stream";
			if (e instanceof BintuStreamFilter) {
				if (0 === e.tags.length && "player" === this.keyMode) return "function" == typeof i && i({
					error: "no tags set",
					request: {
						responseText: "no response error"
					}
				});
				o += e.getQueryString()
			}
			var r = new n("GET", o);
			r.header = "api" === this.keyMode ? {
				Accept: "application/json",
				"Content-Type": "application/json",
				"X-BINTU-APIKEY": this.apiKey
			} : {
				Accept: "application/json",
				"Content-Type": "application/json",
				"X-BINTU-PLAYERKEY": this.playerKey
			}, r.onSuccess = t, r.onError = i, r.Send()
		}, t.tagStream = function (e, t, i, r) {
			if (!this.apiUrl) return "function" == typeof r && r({
				error: "no api url set",
				request: {
					responseText: "no response error"
				}
			});
			if (!this.apiKey) return "function" == typeof r && r({
				error: "no api key set",
				request: {
					responseText: "no response error"
				}
			});
			if ("api" !== this.keyMode) return "function" == typeof r && r({
				error: "wrong key mode set",
				request: {
					responseText: "no response error"
				}
			});
			if (!e) return "function" == typeof r && r({
				error: "no stream id set"
			});
			var o = new n("PUT", this.apiUrl + "/stream/" + e + "/tag");
			o.header = {
				Accept: "application/json",
				"Content-Type": "application/json",
				"X-BINTU-APIKEY": this.apiKey
			}, o.onSuccess = i, o.onError = r, "object" == typeof t && "function" == typeof t.push && t.length > 0 && ("string" == typeof t[0] || t[0] instanceof String) ? o.Send({
				tags: t
			}) : o.Send()
		}, e
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		"use strict";

		function e() {
			this.state = this.setState(e.STATE.ALL), this.tags = []
		}
		var t = e.prototype;
		return e.STATE = Object.create({
			LIVE: "live",
			CREATED: "created",
			ENDED: "ended",
			ALL: null
		}), t.setState = function (t) {
			var n = void 0;
			for (var i in e.STATE) e.STATE[i] === t && (n = e.STATE[i]);
			if ("undefined" == typeof n) throw new Error("The param 'state' must be of type 'BintuStreamFilter.STATE'");
			return this.state = n, this
		}, t.addTag = function (e) {
			if (!(e.length > 0 && ("string" == typeof e || e instanceof String))) throw new Error("The param 'tag' must be of type 'string' and also may not be empty string.");
			return this.tags.push(e), this.tags = this._reduceDuplicates(this.tags), this
		}, t.addTags = function (e) {
			if ("object" != typeof e || "function" != typeof e.push || !(0 === e.length || e.length > 0 && ("string" == typeof e[0] || e[0] instanceof String))) throw new Error("The param 'tags' must be of type 'string array'");
			return this.tags = this.tags.concat(e), this.tags = this._reduceDuplicates(this.tags), this
		}, t.getQueryString = function () {
			var e = "";
			if ("object" == typeof this.tags && "function" == typeof this.tags.push && this.tags.length > 0)
				for (var t = 0; t < this.tags.length; t += 1) "string" == typeof this.tags[t] && (e += 0 === t ? "?" : "&", e += "tags[]=" + this.tags[t]);
			return "string" == typeof this.state && this.state.length > 0 && (e += e.indexOf("?") === -1 ? "?" : "&", e += "state=" + this.state), e
		}, t._reduceDuplicates = function (e) {
			if ("object" != typeof e || "function" != typeof e.push || !(0 === e.length || e.length > 0 && ("string" == typeof e[0] || e[0] instanceof String))) throw new Error("The param 'tags' must be of type 'string array'");
			return e.reduce(function (e, t) {
				return e.indexOf(t) < 0 && e.push(t), e
			}, [])
		}, e
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e, t, n) {
			function i() {
				for (var n in t) t.hasOwnProperty(n) && e.on(t[n], o)
			}

			function r() {
				for (var n in t) t.hasOwnProperty(n) && e.off(t[n], o)
			}

			function o(e) {
				n.emit(e.name, e)
			}
			return i(), {
				destroy: r
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(73), n(75), n(16), n(74), n(14), n(28), n(72), n(79), n(5), n(70), n(81), n(157)], r = function (e, t, n, i, r, o, a, s, c, u, l, d) {
			"use strict";

			function f(t) {
				l.validatePlayerDivId(t), this.version = s.CORE, console.debug("NanoCore api version: " + this.version), this.type = "default", this.id = Math.round(1e11 * Math.random()).toString(), this._playerDivId = t, this._dummyPlayer = new e(this._playerDivId), this._realPlayer = this._dummyPlayer
			}
			var h = f.prototype = Object.create(e.prototype);
			return h.setup = function (e) {
				return new d(function (n, i) {
					var a = e && e.events && e.events.onError ? e.events.onError : null,
						s = function (e) {
							e.code || (e.code = c.SETUP.EXCEPTION), e.message || (e.message = "An unknown error occured during setup.");
							var t = {
								name: "Error",
								data: {
									code: e.code,
									message: e.message
								},
								player: this._playerDivId,
								id: this.id,
								version: this.version,
								state: o.IDLE
							};
							a && this.on(t.name, a), this.emit(t.name, t), i(e)
						}.bind(this),
						l = function (e) {
							n(e)
						};
					try {
						this._realPlayer && this._realPlayer !== this._dummyPlayer && (this._realPlayer.destroy(), this._realPlayer = this._dummyPlayer), t.create(this._playerDivId, e).then(function (t) {
							this.version = t.version, this.type = t.type, this._realPlayer = t, this._realPlayer.id = this.id, this._propagator = u.create(t, r, this), this._realPlayer.setup(e).then(function (e) {
								window.onunload = function (e) {
									this._realPlayer.destroy()
								}.bind(this), l(e)
							}.bind(this), s)
						}.bind(this), function (e) {
							s(e)
						})
					} catch (e) {
						s(e)
					}
				}.bind(this))
			}, h.destroy = function () {
				this._realPlayer.destroy()
			}, h.play = function () {
				this._realPlayer.play()
			}, h.pause = function () {
				this._realPlayer.pause()
			}, h.mute = function () {
				this._realPlayer.mute()
			}, h.unmute = function () {
				this._realPlayer.unmute()
			}, h.setVolume = function (e) {
				this._realPlayer.setVolume(e)
			}, h.updateSource = function (e) {
				return this._realPlayer.updateSource(e)
			}, f.events = r, f.states = o, f.emptyConfig = n, f.validConfig = i, f.pauseReasons = a, f.errorCodes = c, f.version = s.CORE, f.capabilities = t.capabilities, f
		}.apply(t, i),
		/**
		 * @license
		 * nanoStream Player Core
		 * Copyright (c) 2016 nanocosmos IT GmbH. All rights reserved.
		 * http://www.nanocosmos.de
		 * sales@nanocosmos.de
		 *
		 * LEGAL NOTICE:
		 * This material is subject to the terms and conditions defined in
		 * separate license conditions ('LICENSE.txt')
		 * All information contained herein is, and remains the property
		 * of nanocosmos GmbH and its suppliers if any. The intellectual and technical concepts
		 * contained herein are proprietary to nanocosmos GmbH, and are protected by trade secret
		 * or copyright law. Dissemination of this information or reproduction of this material
		 * is strictly forbidden unless prior written permission is obtained from nanocosmos.
		 * All modifications will remain property of nanocosmos.
		 */
		!(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			NORMAL: "normal",
			BUFFER: "buffer",
			CONNECTION_CLOSE: "connectionclose",
			SERVER_NOT_FOUND: "servernotfound",
			STREAM_NOT_FOUND: "streamnotfound",
			INTERACTION_REQUIRED: "interactionrequired",
			PLAYBACK_SUSPENDED: "playbacksuspended",
			PLAYBACK_ERROR: "playbackerror",
			RECONNECTION_IMMINENT: "reconnectionimminent",
			DESTROY: "destroy",
			PLAYBACK_RESTART: "playbackrestart",
			VISIBILITY_HIDDEN: "visibilityhidden",
			NOT_ENOUGH_DATA: "notenoughdata",
			SOURCE_STREAM_STOPPED: "sourcestreamstopped"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(11)], r = function (e) {
		"use strict";

		function t() {
			this.version = void 0, this.type = void 0
		}
		var n = t.prototype = Object.create(e.prototype);
		return n.setup = function () {
			throw new Error('"setup" must be implemented or player not initialized')
		}, n.destroy = function () {
			throw new Error('"destroy" must be implemented or player not initialized')
		}, n.play = function () {
			throw new Error('"play" must be implemented or player not initialized by call setup')
		}, n.pause = function () {
			throw new Error('"pause" must be implemented or player not initialized by call setup')
		}, n.mute = function () {
			throw new Error('"mute" must be implemented or player not initialized by call setup')
		}, n.unmute = function () {
			throw new Error('"unmute" must be implemented or player not initialized by call setup')
		}, n.setVolume = function () {
			throw new Error('"setVolume" must be implemented or player not initialized by call setup')
		}, n.updateSource = function () {
			throw new Error('"updateSource" must be implemented or player not initialized by call setup')
		}, t.isSupported = function () {
			return !1
		}, t.supportedTechniques = [], t
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {
			return {
				source: {
					hls: "string",
					h5live: {
						server: {
							websocket: "string",
							progressive: "string",
							hls: "string"
						},
						token: "string",
						rtmp: {
							url: "string",
							streamname: "string"
						},
						params: "object",
						security: {
							token: "string",
							expires: "string",
							options: "string",
							tag: "string"
						}
					}
				},
				playback: {
					autoplay: "boolean",
					automute: "boolean",
					muted: "boolean",
					forceTech: "string",
					metadata: "boolean",
					flashplayer: "string",
					videoId: "string",
					keepConnection: "boolean",
					allowSafariHlsFallback: "boolean",
					reconnect: {
						minDelay: "number",
						maxDelay: "number",
						delaySteps: "number",
						maxRetries: "number"
					},
					timeouts: {
						loading: "number",
						buffering: "number",
						connecting: "number"
					}
				},
				events: {
					onReady: "function",
					onPlay: "function",
					onPause: "function",
					onLoading: "function",
					onStartBuffering: "function",
					onStopBuffering: "function",
					onError: "function",
					onMetaData: "function",
					onStats: "function",
					onMute: "function",
					onUnmute: "function",
					onVolumeChange: "function",
					onStreamInfo: "function",
					onStreamInfoUpdate: "function",
					onWarning: "function",
					onDestroy: "function"
				},
				tweaks: {
					buffer: {
						max: "number",
						min: "number",
						start: "number",
						target: "number",
						limit: "number"
					},
					bufferDynamic: {
						offsetThreshold: "number",
						offsetStep: "number",
						cooldownTime: "number"
					}
				}
			}
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(3), n(76), n(156), n(82), n(5)], r = function (e, t, n, i, r, o) {
		function a(e) {
			var n = e.filter(function (e) {
				return e.isSupported()
			});
			if (!n.length) throw new t(i.CLIENT_NOT_SUPPORTED, o.SETUP.CLIENT_NOT_SUPPORTED);
			return n
		}

		function s(e, n) {
			var a = {};
			a[r.H5LIVE_WSS] = "h5live", a[r.H5LIVE_HLS] = "h5live", a[r.FLASH] = "h5live", a[r.NATIVE] = "hls";
			var s = [],
				c = e.filter(function (e) {
					var t = !1;
					return e.supportedTechniques.forEach(function (e) {
						s.push(a[e]), t === !1 && (t = n.hasOwnProperty("source") && n.source.hasOwnProperty(a[e]))
					}), t
				});
			if (!c.length) throw new t(i.SOURCE_NOT_SUPPORTED, o.SETUP.SOURCE_NOT_SUPPORTED);
			return c
		}

		function c(e, n) {
			var r = [],
				a = function (e, t) {
					return e.indexOf(t) !== -1 || t.indexOf(e) !== -1
				},
				s = e.filter(function (e) {
					r = r.concat(e.supportedTechniques);
					var t = !1;
					return e.supportedTechniques.forEach(function (e) {
						t === !1 && (t = !n.playback.forceTech || a(e, n.playback.forceTech))
					}), t
				});
			if (!s.length) throw new t(i.FORCE_NOT_SUPPORTED.replace("$tech$", n.playback.forceTech), o.SETUP.FORCE_NOT_SUPPORTED);
			return s
		}

		function u(e, t) {
			var n = e[0];
			return new n(t)
		}

		function l(e, t) {
			return new Promise(function (i, r) {
				var o = n;
				try {
					o = a(o), o = s(o, t), o = c(o, t), i(u(o, e))
				} catch (e) {
					r(e)
				}
			})
		}

		function d() {
			var e = [];
			return n.filter(function (e) {
				return e.isSupported()
			}).forEach(function (t) {
				e = e.concat(t.supportedTechniques)
			}), e
		}
		return {
			create: l,
			capabilities: d()
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(77), n(77), n(151), n(152)], r = function () {
		for (var e = [], t = 0; t < arguments.length; t++) e.push(arguments[t]);
		return e
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(73), n(29), n(78), n(79), n(9), n(10), n(2), n(11), n(80), n(81), n(12), n(16), n(74), n(28), n(83), n(14), n(5), n(84), n(82), n(72), n(85), n(90), n(91), n(93), n(95), n(113), n(122), n(86), n(88), n(87), n(89), n(97)], r = function (e, t, n, i, r, o, a, s, c, u, l, d, f, h, E, p, m, _, T, y, v, g, S, A, N, I, R, O, C, b, P, D) {
		"use strict";

		function w(e) {
			u.validatePlayerDivId(e), this._setBaseValues(e)
		}
		var L = o.log;
		w.getSupportedTechniques = function () {
			var e = [];
			return l.isH5LiveKnownUnsupported || (l.hasMediaSource && e.push(T.H5LIVE_WSS), (l.mustUseHLS || l.canUseHLS) && e.push(T.H5LIVE_HLS)), e
		}, w.supportedTechniques = w.getSupportedTechniques(), w.isSupported = function () {
			return w.supportedTechniques.length > 0
		};
		var U = w.prototype = Object.create(e.prototype);
		return U._setBaseValues = function (e) {
			this.version = i.CORE, this.type = "h5live", this.networkState = this.NETWORK_STATE.UNINITIALIZED, this._info = r, this.config = d.create(), this._playerDivId = "", this._playing = !1, this._streamInfo = null, this._pauseReason = "", this._playerDivId = e, this._mediaElementId = "h5live-" + e, this._componentEmitter = new s, this._componentListeners = {
				logic: [{
					type: C.SERVER_INFO,
					listener: this._onServerInfo.bind(this)
				}, {
					type: C.STREAM_INFO,
					listener: this._onStreamInfo.bind(this)
				}, {
					type: C.STREAM_INFO_UPDATE,
					listener: this._onStreamInfoUpdate.bind(this)
				}, {
					type: C.NO_KEEP_CONNECTION,
					listener: this._onNoKeepConnection.bind(this)
				}, {
					type: C.MIME_TYPE_UNSUPPORTED,
					listener: this._onMimeTypeUnsupported.bind(this)
				}, {
					type: C.ERROR,
					listener: this._onLogicError.bind(this)
				}],
				network: [{
					type: P.CONNECTING,
					listener: this._onConnectionConnecting.bind(this)
				}, {
					type: P.CONNECTED,
					listener: this._onConnectionOpen.bind(this)
				}, {
					type: P.DISCONNECTED,
					listener: this._onConnectionClosed.bind(this)
				}, {
					type: P.RECONNECTING,
					listener: this._onConnectionReconnecting.bind(this)
				}, {
					type: P.RECONNECTION_IMMINENT,
					listener: this._onConnectionReconnectionImminent.bind(this)
				}, {
					type: P.DESTROYED,
					listener: this._onConnectionDestroyed.bind(this)
				}, {
					type: P.INITIALIZATION_ERROR,
					listener: this._onConnectionInitializationError.bind(this)
				}, {
					type: P.ERROR,
					listener: this._onConnectionError.bind(this)
				}],
				media: [{
					type: b.ERROR,
					listener: this._onError.bind(this)
				}, {
					type: b.PLAYBACK_ERROR,
					listener: this._onMediaError.bind(this)
				}, {
					type: b.PLAYBACK_STARTED,
					listener: this._onMediaPlaying.bind(this)
				}, {
					type: b.PLAYBACK_SUSPENDED,
					listener: this._onMediaSuspended.bind(this)
				}, {
					type: b.BUFFERING,
					listener: this._onMediaBuffering.bind(this)
				}, {
					type: b.PLAY_START_ERROR,
					listener: this._onPlayStartError.bind(this)
				}]
			}, this._setState(this.STATE.IDLE)
		}, U._baseSetup = function (e, i) {
			var r = a.check(i, f.create());
			a.merge(i, this.config), a.clean(this.config);
			var o;
			if (o = u.validateConfig(e, i)) return o;
			i.playback.videoId && (this._mediaElementId = i.playback.videoId), this._setListeners(this.config.events, this), r.forEach(function (e) {
				this._emitWarning(e)
			}.bind(this));
			var s = document.getElementById(this._playerDivId);
			this._metaDataEnabled = this.config.playback.metadata;
			var c = this.config.source.h5live.params,
				l = this.config.source.h5live.rtmp,
				d = c && c.url ? c.url : l && l.url ? l.url : null,
				h = c && c.stream ? c.stream : l && l.streamname ? l.streamname : null;
			d && h ? this.config.url = d + "/" + h : this.config.url = "", this.config.type = this.type, this.config.id = this.id, this._components = {
				network: I.create(this._componentEmitter),
				media: R.create(this._componentEmitter),
				logic: N.create(this._componentEmitter),
				performance: S.create(this._componentEmitter, this._playerDivId),
				propagator: g.create(this._componentEmitter),
				error: A.create(this._componentEmitter),
				state: v.create(this._componentEmitter)
			};
			for (var E in this._components) this._componentListeners.hasOwnProperty(E) && this._componentListeners[E].length && t.add({
				target: this._componentEmitter,
				listeners: this._componentListeners[E]
			});
			for (var m in p)(this._outwardEvents || (this._outwardEvents = [])) && this._outwardEvents.push({
				from: p[m],
				to: p[m]
			});
			return this._outwardTranslator = n.create(this._componentEmitter, this, this._outwardEvents), this._emitConfig(), this._setupVideoElement(s), o
		}, U.setup = function (e) {
			return new Promise(function (t, n) {
				try {
					var i = this._baseSetup(w.supportedTechniques[0], e);
					i ? n(i) : (setTimeout(function () {
						this._setState(this.STATE.READY), this.emit(p.READY, {
							config: this.config
						}), this.config.playback.autoplay && this.play()
					}.bind(this), 20), t(this.config))
				} catch (e) {
					n(e)
				}
			}.bind(this))
		}, U._destroy = function () {
			this._setState(this.STATE.DESTROYING), this._playing && this.pause(this.state), this.emit(p.DESTROY), this._outwardTranslator && this._outwardTranslator.destroy(), this._outwardTranslator = null;
			for (var e in this._components) this._components.hasOwnProperty(e) && (this._components[e].destroy(), delete this._components[e]);
			for (var n in this._componentListeners) this._componentListeners.hasOwnProperty(n) && this._componentListeners[n].length && t.remove({
				target: this._componentEmitter,
				listeners: this._componentListeners[n]
			});
			this.removeAllListeners()
		}, U.destroy = U._destroy, U._emitConfig = function () {
			this._componentEmitter.emit(O.CONFIG, {
				config: c.copy(this.config)
			})
		}, U._setState = function (e) {
			var t = arguments.length > 1 ? arguments[1] : {};
			this.state = e, t.state = e, this._componentEmitter.emit(C.STATE_CHANGE, t)
		}, U._debug = function (e, t) {
			L("nanoplayer (" + this._playerDivId + "): " + e, t)
		}, U._setListeners = function (e, t) {
			for (var n in e)
				if (e.hasOwnProperty(n) && "function" == typeof e[n]) {
					var i = n.replace("on", ""),
						r = e[n];
					t.on(i, r)
				}
		}, U._setupVideoElement = function (e) {
			this._componentEmitter.emit(O.CREATE_VIDEO, {
				elementId: this._mediaElementId,
				container: e
			})
		}, U._onLogicError = function (e) {
			switch (e.data.code) {
				case m.PLAYER.VISIBILITY_HIDDEN:
					this._setState(h.VISIBILITY_HIDDEN);
					break;
				case m.STREAM.NOT_ENOUGH_DATA:
					this._setState(h.NOT_ENOUGH_DATA);
					break;
				case m.STREAM.SOURCE_STOPPED:
					this._setState(h.SOURCE_STREAM_STOPPED);
					break;
				case m.MEDIA.SOURCE_ENDED:
					this._setState(h.PLAYBACK_ERROR)
			}
			this.emit(p.ERROR, e.data), this.pause(this.state)
		}, U._onPlayStartError = function (e) {
			switch (this._debug(e.data.error.name + ": " + e.data.error.message, 1), e.data.error.name) {
				case "NotAllowedError":
					e.data.automuted ? this._emitWarning("Unmuted autoplay failed by autoplay policy, but 'automute' is enabled. Try to play muted.") : (this._setState(this.STATE.PLAYBACK_NOT_STARTED), this._emitError(m.PLAYER.INTERACTION_REQUIRED, "Playback must be initialized by user gesture."))
			}
		}, U._onError = function (e) {
			if (this.state !== this.STATE.PAUSING && this.state !== this.STATE.PAUSED) {
				var t = "",
					n = e.data.code;
				switch (n) {
					case MediaError.MEDIA_ERR_ABORTED:
						t = "A fetching process of the media aborted by user";
						break;
					case MediaError.MEDIA_ERR_NETWORK:
						t = "An error occurred when downloading media";
						break;
					case MediaError.MEDIA_ERR_DECODE:
						t = "An error occurred when decoding media";
						break;
					case MediaError.MEDIA_ERR_SRC_NOT_SUPPORTED:
						t = "The received audio/video is not supported";
						break;
					default:
						t = "An unknown media error occurred"
				}
				this._debug('error media with state "' + this._getState() + '", networkstate "' + this._getNetworkState() + '"', 3), this._debug("error message: " + t, 3), n === MediaError.MEDIA_ERR_DECODE && this.state !== this.STATE.LOADING && setTimeout(this.play.bind(this), 1e3), this._setState(this.STATE.PLAYBACK_ERROR), this._emitError(n + 3e3, t)
			}
		}, U._preparePlay = function (e) {
			this._setState(this.STATE.LOADING, {
				connectDelay: e
			}), this.emit(p.LOADING, {
				connectDelay: e
			})
		}, U.play = function () {
			var e = arguments.length ? arguments[0] : 0;
			L("nanoplayer (" + this._playerDivId + "): play in state " + this.state, 1), this.state < this.STATE.READY ? this._emitError(m.PLAYER.NOT_CONFIGURED, "Could not play because player has not been configured.") : this.state === this.STATE.READY || this.state === this.STATE.PAUSED ? (this._playing = !0, e ? setTimeout(function () {
				this.state === this.STATE.LOADING && (this._componentEmitter.emit(D.PLAY), this._componentEmitter.emit(O.PLAY, {
					external: !0
				}))
			}.bind(this), e) : (this._componentEmitter.emit(D.PLAY), this._componentEmitter.emit(O.PLAY, {
				external: !0
			})), this._preparePlay(e)) : this.state === this.STATE.PAUSING && (L("nanoplayer (" + this._playerDivId + "play in PAUSING state, setting flag to resume"), this._resumeOnPause = !0)
		}, U._onConnectionInitializationError = function (e) {
			this._debug('error init connection with state "' + this._getState() + '", networkstate "' + this._getNetworkState() + '"', 2), this.networkState = e.connectionState;
			var t = "Connection initialization error.",
				n = e ? e.data : void 0;
			n && n.message && (t = n.message), n && n.code && (t += " (code:" + n.code + ")"), n && n.name && (t = n.name + ": " + t), this._setState(this.STATE.CONNECTION_ERROR), this._emitError(m.NETWORK.COULD_NOT_ESTABLISH_CONNECTION, t)
		}, U._onConnectionError = function (e) {
			this._debug('error connection with state "' + this._getState() + '", networkstate "' + this._getNetworkState() + '"', 2), this.networkState = e.connectionState;
			var t = "Connection error.",
				n = e ? e.data : void 0;
			n && n.message && (t = n.message), n && n.code && (t += " (code:" + n.code + ")"), n && n.name && (t = n.name + ": " + t), this._setState(this.STATE.CONNECTION_ERROR), this._emitError(m.NETWORK.CONNECTION_ERROR, t)
		}, U._onConnectionConnecting = function (e) {
			this._debug("connecting", 1), this.networkState = e.connectionState
		}, U._onConnectionOpen = function (e) {
			e.data.count ? this._debug("connection open after " + e.data.count + " reconnect tries", 1) : this._debug("connection open", 1), this.networkState = e.connectionState
		}, U._onConnectionClosed = function (e) {
			this._debug("connection closed", 1), this.networkState = e.connectionState;
			var t;
			t = _.hasOwnProperty(+e.data.code) ? _[+e.data.code] : "Unknown reason";
			var n = e.data.code;
			n > 1e3 && n < 4e3 && (n += 3100), this._debug('closed connection with state "' + this._getState() + '", networkstate "' + this._getNetworkState() + '"', 2), n > 4e3 && this._emitError(n, t)
		}, U._onConnectionDestroyed = function (e) {
			this._debug("connection destroyed" + (e.data.silent ? ", but silent" : ""), 1), this.networkState = e.connectionState, this.state === this.STATE.READY || e.data.silent || this.pause(this.state)
		}, U._onConnectionReconnecting = function (e) {
			this._debug("reconnect attempt " + e.data.count + " started", 1), this.networkState = e.connectionState
		}, U._onConnectionReconnectionImminent = function (e) {
			var t = e.data.code;
			t > 1e3 && t < 4e3 && (t += 3100), this._debug("connection " + e.data.count + (e.data.count > 1 ? " times" : " time") + " unexpectedly closed with code " + t + ", but a reconnect will be prepared", 1), this._debug("reconnect attempt " + e.data.count + " starts in " + Math.round(e.data.delay) + " ms", 1), ("iOS" !== r.os || "iOS" === r.os && e.data.count > 1) && this._emitWarning("Connection error: closed " + e.data.count + (e.data.count > 1 ? " times" : " time") + " unexpectedly with code " + t + ", but a reconnect will be prepared in " + (e.data.delay / 1e3).toFixed(3) + " s"), this.networkState = e.connectionState, this._playing && "iOS" !== r.os && (this._setState(this.STATE.RECONNECTION_IMMINENT), this.pause(this.state), this.play(e.data.delay))
		}, U._onServerInfo = function (e) {
			this._debug("onServerInfo", 2);
			try {
				var t = JSON.stringify(e.data);
				this._debug("" + t, 3)
			} catch (e) {}
		}, U._onStreamInfo = function (e) {
			this._debug("onStreamInfo", 2), this.emit(p.STREAM_INFO, e.data)
		}, U._onStreamInfoUpdate = function (e) {
			this._debug("onStreamInfoUpdate", 2), this.emit(p.STREAM_INFO_UPDATE, e.data)
		}, U._onNoKeepConnection = function (e) {
			this._debug("onNoKeepConnection", 2), this._emitWarning(e.data.message)
		}, U._onMimeTypeUnsupported = function () {
			this._setState(this.STATE.PLAYBACK_ERROR), this._emitError(m.MEDIA.NOT_SUPPORTED, "The received audio/video is not supported")
		}, U._onMediaError = function (e) {
			this._setState(this.STATE.PLAYBACK_ERROR), this._emitError(m.PLAYER.PLAYBACK_ERROR, e.data)
		}, U._onMediaSuspended = function () {
			r.mobile ? (this._setState(this.STATE.PLAYBACK_SUSPENDED), this._emitError(m.PLAYER.PLAYBACK_SUSPENDED, "Playback suspended by external reason.")) : (this._setState(h.PLAYBACK_RESTARTING), this.pause(this.state), setTimeout(this.play.bind(this), 0))
		}, U._emit = U.emit, U.emit = function (e, t) {
			var n = {};
			t && t.name && t.data ? n = t : t ? (n.data = t, n.name = e || "unknown") : (n.data = {}, n.name = e || "unknown"), n.player = this._playerDivId, n.id = this.id, n.version = this.version, n.state = this.state, "Error" === e && n.data.code && n.data.message && this._debug("error " + n.data.code + " " + n.data.message, 1), this._emit(n.name, n)
		}, U._emitError = function (e, t) {
			this.emit(p.ERROR, {
				code: e,
				message: t
			}), this.pause(this.state)
		}, U._emitWarning = function (e) {
			this.emit(p.WARNING, {
				message: e
			})
		}, U._getPauseReason = function (e) {
			var t = "";
			if (e) {
				var n = this.networkState !== this.NETWORK_STATE.OPEN && this.networkState !== this.NETWORK_STATE.UNINITIALIZED;
				switch (e) {
					case this.STATE.READY:
						t = y.SERVER_NOT_FOUND;
						break;
					case this.STATE.LOADING:
						t = n ? y.CONNECTION_CLOSE : y.STREAM_NOT_FOUND;
						break;
					case this.STATE.BUFFERING:
						t = n ? y.CONNECTION_CLOSE : y.BUFFER;
						break;
					case this.STATE.UNKNOWN:
						t = n ? y.CONNECTION_CLOSE : y.UNKNOWN;
						break;
					case this.STATE.PLAYING:
						t = n ? y.CONNECTION_CLOSE : y.NORMAL;
						break;
					case this.STATE.PLAYBACK_NOT_STARTED:
						t = y.INTERACTION_REQUIRED;
						break;
					case this.STATE.PLAYBACK_SUSPENDED:
						t = y.PLAYBACK_SUSPENDED;
						break;
					case this.STATE.PLAYBACK_ERROR:
						t = y.PLAYBACK_ERROR;
						break;
					case this.STATE.RECONNECTION_IMMINENT:
						t = y.RECONNECTION_IMMINENT;
						break;
					case this.STATE.CONNECTION_ERROR:
						t = y.CONNECTION_CLOSE;
						break;
					case this.STATE.DESTROYING:
						t = y.DESTROY;
						break;
					case this.STATE.PLAYBACK_RESTARTING:
						t = y.PLAYBACK_RESTART;
						break;
					case this.STATE.VISIBILITY_HIDDEN:
						t = y.VISIBILITY_HIDDEN;
						break;
					case this.STATE.NOT_ENOUGH_DATA:
						t = y.NOT_ENOUGH_DATA;
						break;
					case this.STATE.SOURCE_STREAM_STOPPED:
						t = y.SOURCE_STREAM_STOPPED;
						break;
					default:
						t = y.NORMAL
				}
			} else t = y.NORMAL;
			return t
		}, U._triggerPause = function () {
			return this.state === this.STATE.PAUSED ? void L("nanoplayer (" + this._playerDivId + "): trigger pause but already in state paused") : (L("nanoplayer (" + this._playerDivId + '): trigger pause with reason "' + this._pauseReason + '", state "' + this._getState() + '", networkstate "' + this._getNetworkState() + '"', 3), this._setState(this.STATE.PAUSED), this.emit(p.PAUSE, {
				reason: this._pauseReason
			}), void(this._resumeOnPause && (this._resumeOnPause = !1, this.play())))
		}, U.pause = function () {
			var e = arguments.length ? arguments[0] : 0;
			L("nanoplayer (" + this._playerDivId + "): pause in state " + this.state, 2), this.state < this.STATE.READY ? this._emitError(m.PLAYER.NOT_PLAYING, "Could not pause because player was not in playing state before.") : this.state !== this.STATE.PAUSED && this.state !== this.STATE.PAUSING && (this._pauseReason = this._getPauseReason(e), this._setState(this.STATE.PAUSING), this._playing = !1, this._resumeOnPause = !1, this._componentEmitter.emit(D.PAUSE), this._componentEmitter.emit(O.PAUSE), this._triggerPause())
		}, U.mute = function () {
			this._componentEmitter.emit(O.MUTE)
		}, U.unmute = function () {
			this._componentEmitter.emit(O.UNMUTE)
		}, U.setVolume = function (e) {
			this._componentEmitter.emit(O.SET_VOLUME, {
				volume: e
			})
		}, U.updateSource = function (e) {
			return new Promise(function (t, n) {
				var i;
				if (i = u.validateConfig(w.supportedTechniques[0], {
						source: e
					})) i.message.replace("create", "update"), this._emitError(i.code, i.message), n(i);
				else {
					a.merge(e, this.config.source), a.clean(this.config.source);
					var r = this.config.source.h5live.params,
						o = this.config.source.h5live.rtmp,
						s = r && r.url ? r.url : o && o.url ? o.url : null,
						c = r && r.stream ? r.stream : o && o.streamname ? o.streamname : null;
					s && c ? this.config.url = s + "/" + c : this.config.url = "", this._componentEmitter.emit(O.UPDATE_SOURCE, {
						source: e
					}), this.state !== this.STATE.PLAYING && this.state !== this.STATE.LOADING && this.state !== this.STATE.BUFFERING || (this._setState(h.PLAYBACK_RESTARTING), this.pause(this.state), setTimeout(this.play.bind(this), 0)), t(this.config)
				}
			}.bind(this))
		}, U._parseUrl = function (e) {
			var t = {
					url: "",
					streamname: ""
				},
				n = e.lastIndexOf("/"),
				i = e.substr(0, n),
				r = e.substr(n, e - n);
			return t.url = i, t.streamname = r, t
		}, U._getState = function () {
			return c.findPropertyByValue(this.STATE, this.state)
		}, U._getNetworkState = function () {
			return c.findPropertyByValue(this.NETWORK_STATE, this.networkState)
		}, U._onMediaPlaying = function (e) {
			var t = this.state;
			this._setState(h.PLAYING), this.emit(t === h.BUFFERING ? p.STOP_BUFFERING : p.PLAY, e.data), this._debug("state is PLAYING")
		}, U._onMediaBuffering = function () {
			this._setState(h.BUFFERING), this.emit(p.START_BUFFERING)
		}, U.STATE = h, U.NETWORK_STATE = E, U.PAUSE_REASON = y, w
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(29)], r = function (e) {
		function t(t, n, i) {
			function r() {
				s = [], i.forEach(function (e) {
					s.push({
						type: e.from,
						listener: a
					}), c[e.from] || (c[e.from] = []), c[e.from].push(e.to)
				}), e.add({
					target: t,
					listeners: s
				})
			}

			function o() {
				e.remove({
					target: t,
					listeners: s
				}), s = null, c = null
			}

			function a(e) {
				c[e.name] && c[e.name].forEach(function (t) {
					var i = {};
					for (var r in e) e.hasOwnProperty(r) && (i[r] = e[r]);
					n.emit(t, i)
				})
			}
			var s, c = {};
			return r(), {
				destroy: o
			}
		}
		return {
			create: t
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return {
			CORE: "3.18.3"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(t) {
			var i;
			switch (typeof t) {
				case "object":
					if (null === t) i = null;
					else switch (n.call(t)) {
						case "[object Array]":
							i = t.map(e);
							break;
						case "[object Date]":
							i = new Date(t);
							break;
						case "[object RegExp]":
							i = new RegExp(t);
							break;
						default:
							i = Object.keys(t).reduce(function (n, i) {
								return n[i] = e(t[i]), n
							}, {})
					}
					break;
				default:
					i = t
			}
			return i
		}

		function t(e, t) {
			if ("object" == typeof e)
				for (var n in e)
					if (e[n] === t) return n;
			return !1
		}
		var n = Object.prototype.toString;
		return {
			copy: e,
			findPropertyByValue: t
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(3), n(4), n(82), n(5)], r = function (e, t, n, i) {
		function r(r, o) {
			var a = o.source;
			if (!a || !a.h5live) return new e(t.CONFIG_SOURCE, i.SETUP.SOURCE_NOT_SUPPORTED);
			var s = o.source.h5live;
			if (!(s.rtmp && s.rtmp.url && s.rtmp.streamname || s.params && s.params.url && s.params.stream)) return new e(t.CONFIG_RTMP, i.SETUP.CONFIG_RTMP);
			if (r === n.H5LIVE_WSS && (!s.server || s.server && !s.server.websocket)) return new e(t.CONFIG_WSS, i.SETUP.CONFIG_WSS);
			if (r === n.H5LIVE_HLS) {
				if (!s.server || s.server && !s.server.hls) return new e(t.CONFIG_HLS, i.SETUP.CONFIG_HLS);
				if (o.playback && o.playback.metadata && !s.server.websocket) return new e(t.CONFIG_METADATA, i.SETUP.CONFIG_METADATA)
			}
			return !(s.server && (s.server.websocket || s.server.hls) || s.token) && new e(t.CONFIG_TOKEN, i.SETUP.CONFIG_TOKEN)
		}

		function o(t) {
			if (!("string" == typeof t || t instanceof String)) throw new e('The param "playerDivId" must be of type "String".');
			var n = document.getElementById(t);
			if (!n) throw new e('The param "playerDivId" must be the id of an existing "div" element.');
			if ("DIV" !== n.tagName) throw new e('The element with the id "' + t + '" is not a "div" element.');
			return !0
		}
		return {
			validateConfig: r,
			validatePlayerDivId: o
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			FLASH: "flash",
			NATIVE: "hls.native",
			H5LIVE_WSS: "h5live",
			H5LIVE_HLS: "h5live.hls"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			UNINITIALIZED: 1,
			CONNECTING: 2,
			OPEN: 3,
			CLOSING: 4,
			CLOSED: 5
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			1000: "Normal closure, meaning that the purpose for which the connection was established has been fulfilled.",
			1001: 'An endpoint is "going away", such as a server going down or a browser having navigated away from a page.',
			1002: "An endpoint is terminating the connection due to a protocol error",
			1003: "An endpoint is terminating the connection because it has received a type of data it cannot accept (e.g., an endpoint that understands only text data MAY send this if it receives a binary message).",
			1004: "Reserved. The specific meaning might be defined in the future.",
			1005: "No status code was actually present.",
			1006: "Maybe no network, wrong url or server down.",
			1007: "An endpoint is terminating the connection because it has received data within a message that was not consistent with the type of the message (e.g., non-UTF-8 [http://tools.ietf.org/html/rfc3629] data within a text message).",
			1008: 'An endpoint is terminating the connection because it has received a message that "violates its policy". This reason is given either if there is no other sutible reason, or if there is a need to hide specific details about the policy.',
			1009: "An endpoint is terminating the connection because it has received a message that is too big for it to process.",
			1011: "A server is terminating the connection because it encountered an unexpected condition that prevented it from fulfilling the request.",
			1015: "The connection was closed due to a failure to perform a TLS handshake (e.g., the server certificate can't be verified).",
			4400: "Bad request. Maybe stream parameters are missing or malformed.",
			4403: "Access denied. The authentication token is missing or invalid.",
			4500: "The connection has been rejected due an internal server error.",
			4503: "The requested service is currently unavailable.",
			4900: "The security service has been rejected due an internal server error.",
			4901: "The security service denied access. The authentication token is invalid.",
			4903: "The security service denied access. The url is expired or a token parameter is missing (expires, token, or options).",
			4904: "The security service can not be found."
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(29), n(14), n(86), n(87), n(88), n(89), n(13)], r = function (e, t, n, i, r, o, a) {
		function s(n) {
			function i() {
				e.add({
					target: n,
					listeners: l
				})
			}

			function o() {
				e.remove({
					target: n,
					listeners: l
				})
			}

			function s(e, t) {
				n.emit(e, t)
			}

			function c(e) {
				u = e.data.state, s(t.STATE_CHANGE, e.data)
			}
			var u, l = (a.create("StateMachine"), [{
				type: r.STATE_CHANGE,
				listener: c
			}]);
			return i(), {
				destroy: o
			}
		}
		return {
			create: s
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "control.";
		return {
			BASE: e,
			CONNECT: e + "connect",
			DISCONNECT: e + "disconnect",
			NETWORK_PLAY: e + "networkPlay",
			NETWORK_PAUSE: e + "networkPause",
			PLAY: e + "play",
			PAUSE: e + "pause",
			MUTE: e + "mute",
			UNMUTE: e + "unmute",
			SET_VOLUME: e + "setVolume",
			SEEK: e + "seek",
			SET_RATE: e + "playbackRate",
			CONFIG: e + "config",
			CREATE_VIDEO: e + "createVideo",
			DESTROY_VIDEO: e + "destroyVideo",
			VIDEO_SOURCE: e + "videoSource",
			UPDATE_SOURCE: e + "updateSource"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "media.";
		return {
			BASE: e,
			LOAD_START: e + "loadstart",
			PROGRESS: e + "progress",
			SUSPEND: e + "suspend",
			ABORT: e + "abort",
			EMPTIED: e + "emptied",
			STALLED: e + "stalled",
			PLAY: e + "play",
			PAUSE: e + "pause",
			LOADED_META_DATA: e + "loadedmetadata",
			LOADED_DATA: e + "loadeddata",
			WAITING: e + "waiting",
			ERROR: e + "error",
			PLAYING: e + "playing",
			CAN_PLAY: e + "canplay",
			CAN_PLAY_THROUGH: e + "canplaythrough",
			SEEKING: e + "seeking",
			SEEKED: e + "seeked",
			TIME_UPDATE: e + "timeupdate",
			ENDED: e + "ended",
			RATE_CHANGE: e + "ratechange",
			DURATION_CHANGE: e + "durationchange",
			VOLUME_CHANGE: e + "volumechange",
			ELEMENT_CREATED: e + "elementCreated",
			QUALITY_STATS: e + "qualitystats",
			PLAY_STATS: e + "playStats",
			FRAME_DROP: e + "framedrop",
			SOURCE_OPEN: e + "sourceopen",
			SOURCE_ENDED: e + "sourceended",
			SOURCE_CLOSED: e + "sourceclosed",
			PLAYBACK_ERROR: e + "playbackError",
			PLAYBACK_STARTED: e + "playbackStarted",
			PLAYBACK_SUSPENDED: e + "playbackSuspended",
			BUFFERING: e + "buffering",
			PLAY_START_SUCCESS: e + "playStartSuccess",
			PLAY_START_ERROR: e + "playStartError",
			BUFFER_TWEAKS_CREATED: e + "bufferTweaksCreated",
			BUFFER_TWEAKS_ERROR: e + "bufferTweaksError",
			VIEWPORT_VISIBLE: e + "viewportvisible",
			VIEWPORT_HIDDEN: e + "viewporthidden"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "logic.";
		return {
			BASE: e,
			STATE_CHANGE: e + "stateChange",
			LOADING_TIMEOUT: e + "loadingTimeout",
			BUFFERING_TIMEOUT: e + "bufferingTimeout",
			STREAM_INFO: e + "streamInfo",
			STREAM_INFO_UPDATE: e + "streamInfoUpdate",
			SERVER_INFO: e + "serverInfo",
			STREAM_URL: e + "streamUrl",
			NO_KEEP_CONNECTION: e + "noKeepConnection",
			MIME_TYPE_UNSUPPORTED: e + "mimeTypeUnsupported",
			META_DATA_RECEIVED: e + "metaDataReceived",
			META_DATA_ERROR: e + "metaDataError",
			NO_SERVER_ERROR: e + "noServerError",
			MISSING_RTMP_ERROR: e + "missingRtmpError",
			STARTUP_STATS: e + "startupStats",
			DOCUMENT_VISIBLE: e + "documentVisible",
			DOCUMENT_HIDDEN: e + "documentHidden",
			ERROR: e + "error"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "network.";
		return {
			BASE: e,
			CONNECTING: e + "connecting",
			CONNECTED: e + "connected",
			DISCONNECTED: e + "disconnected",
			RESUMING: e + "resuming",
			RECONNECTION_IMMINENT: e + "reconnectionImminent",
			RECONNECTING: e + "reconnecting",
			RECONNECTION_CONFIG_INVALID: e + "reconnectionConfigInvalid",
			CONNECTION_ERROR: e + "connectionError",
			INITIALIZATION_ERROR: e + "initializationError",
			MAX_RETRY_REACHED: e + "maxRetryReached",
			ERROR: e + "error",
			DESTROYED: e + "destroyed",
			META_DATA: e + "metaData",
			SERVER_INFO: e + "serverInfo",
			STREAM_INFO: e + "streamInfo",
			STREAM_INFO_UPDATE: e + "streamInfoUpdate",
			STREAM_FRAGMENT: e + "streamFragment",
			RANDOM_ACCESS_POINT: e + "randomAccessPoint",
			RAW_PACKET: e + "raw",
			STREAM_STATUS: e + "streamStatus"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(29), n(5), n(14), n(86), n(87), n(88), n(89), n(13)], r = function (e, t, n, i, r, o, a, s) {
		function c(i) {
			function c() {
				e.add({
					target: i,
					listeners: S
				}), A.forEach(function (e) {
					i.addListener(e.event, d)
				})
			}

			function u() {
				e.remove({
					target: i,
					listeners: S
				}), A.forEach(function (e) {
					i.removeListener(e.event, d)
				})
			}

			function l(e, t) {
				i.emit(e, t)
			}

			function d(e) {
				var t, n;
				A.forEach(function (i) {
					i.event === e.name && (t = i.code ? i.code : e.data.code, n = i.message ? i.message : e.data.reason, i.defer ? setTimeout(f.bind(this, t, n), 0) : f(t, n))
				})
			}

			function f(e, t) {
				l(n.ERROR, {
					code: e,
					message: t
				})
			}

			function h(e) {
				var t;
				T && T.quality && (t = T.quality), T = e.data.stats, t && !T.quality && (T.quality = t);
				var i, r = "stats error";
				try {
					r = JSON.stringify(T), i = JSON.parse(r), delete i.buffer.update, delete i.buffer.delay.before
				} catch (e) {}
				l(n.STATS, {
					stats: i
				})
			}

			function E(e) {
				T || (T = {}), T.quality = e.data
			}

			function p(e) {
				y.debug("mediaSource " + (e.type === r.SOURCE_ENDED ? "ended" : "closed") + ", readyState: " + e.data.readyState), l(n.PLAYBACK_FINISHED)
			}

			function m(e) {
				e.data.muted !== g ? (g = e.data.muted, l(g ? n.MUTE : n.UNMUTE, {
					volume: v
				})) : e.data.volume !== v && (v = e.data.volume, l(n.VOLUME_CHANGE, {
					volume: v
				}))
			}

			function _(e) {
				setTimeout(l.bind(this, n.METADATA, e.data), 0)
			}
			var T, y = s.create("FacadePropagator"),
				v = 1,
				g = !1,
				S = [{
					type: r.VOLUME_CHANGE,
					listener: m
				}, {
					type: r.QUALITY_STATS,
					listener: E
				}, {
					type: r.PLAY_STATS,
					listener: h
				}, {
					type: r.SOURCE_ENDED,
					listener: p
				}, {
					type: r.SOURCE_CLOSED,
					listener: p
				}, {
					type: o.META_DATA_RECEIVED,
					listener: _
				}],
				A = [{
					event: o.META_DATA_ERROR,
					code: null,
					message: null,
					defer: !0
				}, {
					event: o.MISSING_RTMP_ERROR,
					code: t.PLAYER.NO_RTMP_URL_SET,
					message: "No rtmp url set.",
					defer: !1
				}, {
					event: o.NO_SERVER_ERROR,
					code: t.PLAYER.NO_SERVER_SET,
					message: "No server set.",
					defer: !1
				}, {
					event: a.RECONNECTION_CONFIG_INVALID,
					code: t.NETWORK.RECONNECTION_CONFIG_INVALID,
					message: null,
					defer: !0
				}, {
					event: r.BUFFER_TWEAKS_ERROR,
					code: t.PLAYER.BUFFER_CONFIG_INVALID,
					message: null,
					defer: !1
				}];
			return c(), {
				destroy: u
			}
		}
		return {
			create: c
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(92), n(13), n(12), n(29), n(28), n(86), n(87), n(89), n(88)], r = function (e, t, n, i, r, o, a, s, c) {
		function u(t, u) {
			function v() {
				i.add({
					target: t,
					listeners: F
				})
			}

			function g() {
				i.remove({
					target: t,
					listeners: F
				}), i.remove({
					target: t,
					listeners: w ? G : k
				})
			}

			function S(e) {
				w = n.mustUseHLS || e.data.config.playback.allowSafariHlsFallback && n.canUseHLS, i.add({
					target: t,
					listeners: w ? G : k
				})
			}

			function A(e) {
				switch (L = e.data.state) {
					case r.PAUSED:
						M = {}, x = null
				}
			}

			function N(t) {
				var n = [l, u, t].join(d);
				e && "function" == typeof e.mark && U.indexOf(n) === -1 && (e.mark(n), U.push(n)), M[t] || t === T && (t !== T || [p, m, _].indexOf(x) === -1) || (x = t, M[t] = e.now(), y.debug(t + ": " + M[t]), t === T && I())
			}

			function I() {
				if (f.forEach(function (e, t) {
						if (void 0 === M[e]) {
							M[e] = 0;
							for (var n = t + 1, i = f.length; n < i; n += 1)
								if (void 0 !== M[f[n]]) {
									M[e] = M[f[n]];
									break
								}
						}
					}), f.filter(function (e) {
						return void 0 !== M[e]
					}).length) {
					var e = M[h];
					for (var n in M) M.hasOwnProperty(n) && (M[n] = Math.round(M[n] - e));
					t.emit(c.STARTUP_STATS, {
						stats: M
					}), y.debug("startup stats: " + JSON.stringify(M))
				}
			}

			function R() {
				N(h)
			}

			function O() {
				N(E)
			}

			function C() {
				N(_)
			}

			function b() {
				N(T)
			}

			function P() {
				N(p)
			}

			function D() {
				N(m)
			}
			var w, L, U = [],
				M = {},
				x = null,
				F = [{
					type: c.STATE_CHANGE,
					listener: A
				}, {
					type: o.CONFIG,
					listener: S
				}],
				k = [{
					type: s.CONNECTING,
					listener: R
				}, {
					type: s.CONNECTED,
					listener: O
				}, {
					type: s.STREAM_FRAGMENT,
					listener: P
				}, {
					type: a.LOADED_DATA,
					listener: D
				}, {
					type: a.CAN_PLAY_THROUGH,
					listener: C
				}, {
					type: a.PLAYING,
					listener: b
				}],
				G = [{
					type: a.LOAD_START,
					listener: R
				}, {
					type: a.DURATION_CHANGE,
					listener: O
				}, {
					type: a.LOADED_META_DATA,
					listener: P
				}, {
					type: a.LOADED_DATA,
					listener: D
				}, {
					type: a.CAN_PLAY_THROUGH,
					listener: C
				}, {
					type: a.PLAYING,
					listener: b
				}];
			return v(), {
				destroy: g
			}
		}
		var l = "nano",
			d = ".",
			f = [],
			h = f[0] = "connecting",
			E = f[1] = "connected",
			p = f[2] = "firstFragmentReceived",
			m = f[3] = "firstFrameRendered",
			_ = f[4] = "playable",
			T = f[5] = "playing",
			y = t.create("Performance");
		return {
			create: u
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = Date.now ? Date.now() : +new Date,
			t = window.performance || {},
			n = [],
			i = {},
			r = function (e, t) {
				for (var i = 0, r = n.length, o = []; i < r; i++) n[i][e] === t && o.push(n[i]);
				return o
			},
			o = function (e, t) {
				for (var i, r = n.length; r--;) i = n[r], i.entryType !== e || void 0 !== t && i.name !== t || n.splice(r, 1)
			};
		return t.now || (t.now = t.webkitNow || t.mozNow || t.msNow || function () {
			return (Date.now ? Date.now() : +new Date) - e
		}), t.mark || (t.mark = t.webkitMark || function (e) {
			var r = {
				name: e,
				entryType: "mark",
				startTime: t.now(),
				duration: 0
			};
			n.push(r), i[e] = r
		}), t.measure || (t.measure = t.webkitMeasure || function (e, t, r) {
			t = i[t].startTime, r = i[r].startTime, n.push({
				name: e,
				entryType: "measure",
				startTime: t,
				duration: r - t
			})
		}), t.getEntriesByType || (t.getEntriesByType = t.webkitGetEntriesByType || function (e) {
			return r("entryType", e)
		}), t.getEntriesByName || (t.getEntriesByName = t.webkitGetEntriesByName || function (e) {
			return r("name", e)
		}), t.clearMarks || (t.clearMarks = t.webkitClearMarks || function (e) {
			o("mark", e)
		}), t.clearMeasures || (t.clearMeasures = t.webkitClearMeasures || function (e) {
			o("measure", e)
		}), void 0 === window.performance && (window.performance = t), window.performance
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(29), n(5), n(94), n(14), n(28), n(86), n(87), n(88), n(89), n(13), n(9)], r = function (e, t, n, i, r, o, a, s, c, u, l) {
		function d(i) {
			function o() {
				e.add({
					target: i,
					listeners: I
				})
			}

			function l() {
				e.remove({
					target: i,
					listeners: I
				})
			}

			function d(e, t) {
				i.emit(e, t)
			}

			function f(e, t) {
				d(s.ERROR, {
					code: e,
					message: t
				})
			}

			function h(e) {
				v = e.data.state, v === r.LOADING && (S = !1, g = null)
			}

			function E(e) {
				S ? f(t.STREAM.NOT_ENOUGH_DATA, n.STREAM.NOT_ENOUGH_DATA) : g && "stopped" === g ? f(t.STREAM.SOURCE_STOPPED, n.STREAM.SOURCE_STOPPED) : f(t.STREAM.NOT_FOUND, n.STREAM.NOT_FOUND)
			}

			function p(e) {
				g && "stopped" === g ? f(t.STREAM.SOURCE_STOPPED, n.STREAM.SOURCE_STOPPED) : f(t.STREAM.MEDIA_NOT_AVAILABLE, n.STREAM.MEDIA_NOT_AVAILABLE)
			}

			function m(e) {
				S = !0
			}

			function _(e) {
				e.name === s.DOCUMENT_HIDDEN ? A = !0 : (A && (N = !0), A = !1, setTimeout(function (e) {
					N = !1
				}, 1e3))
			}

			function T(e) {
				A || N ? f(t.PLAYER.VISIBILITY_HIDDEN, n.PLAYER.VISIBILITY_HIDDEN) : f(t.MEDIA.SOURCE_ENDED, n.MEDIA.SOURCE_ENDED)
			}

			function y(e) {
				g = e.data.onStreamStatus.status
			}
			var v, g, S, A, N, I = (u.create("ErrorSelector"), [{
				type: s.STATE_CHANGE,
				listener: h
			}, {
				type: s.LOADING_TIMEOUT,
				listener: E
			}, {
				type: s.BUFFERING_TIMEOUT,
				listener: p
			}, {
				type: s.STREAM_INFO,
				listener: m
			}, {
				type: s.DOCUMENT_VISIBLE,
				listener: _
			}, {
				type: s.DOCUMENT_HIDDEN,
				listener: _
			}, {
				type: a.SOURCE_ENDED,
				listener: T
			}, {
				type: c.STREAM_STATUS,
				listener: y
			}]);
			return o(), {
				destroy: l
			}
		}
		return {
			create: d
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			PLAYER: {
				VISIBILITY_HIDDEN: "Playback failed because the player was in visibility state 'hidden' at load start."
			},
			STREAM: {
				NOT_FOUND: "The requested stream can not be found.",
				MEDIA_NOT_AVAILABLE: "No media available.",
				NOT_ENOUGH_DATA: "Not enough media data received.",
				SOURCE_STOPPED: "The source stream has been stopped."
			},
			MEDIA: {
				SOURCE_ENDED: "The media source extension changed the state to 'ended'."
			}
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(11), n(12), n(29), n(78), n(97), n(86), n(88), n(87), n(89), n(98), n(99), n(96), n(100), n(101), n(102), n(103), n(104), n(106), n(107), n(108), n(110), n(111), n(112)], r = function (e, t, n, i, r, o, a, s, c, u, l, d, f, h, E, p, m, _, T, y, v, g, S, A) {
		"use strict";

		function N(N) {
			function I() {
				D.debug("init"), C = r.create(N, P, U), b = r.create(P, N, L), i.add({
					target: P,
					listeners: M
				}), w.push(_.create(P)), w.push(g.create(P)), w.push(v.create(P)), w.push(A.create(P))
			}

			function R() {
				for (C.destroy(), C = null, b.destroy(), b = null; w.length;) w.pop().destroy();
				i.remove({
					target: P,
					listeners: M
				})
			}

			function O(e) {
				w.push(n.mustUseHLS || e.data.config.playback.allowSafariHlsFallback && n.canUseHLS ? y.create(P) : T.create(P)), e.data.config.playback.metadata && w.push(S.create(P))
			}
			var C, b, P = new t,
				D = e.create("LogicManager"),
				w = [],
				L = [{
					from: l.SEEK,
					to: a.SEEK
				}, {
					from: l.RATE,
					to: a.SET_RATE
				}, {
					from: d.CONNECT,
					to: a.CONNECT
				}, {
					from: d.DISCONNECT,
					to: a.DISCONNECT
				}, {
					from: d.VIDEO_SOURCE,
					to: a.VIDEO_SOURCE
				}, {
					from: d.NETWORK_PLAY,
					to: a.NETWORK_PLAY
				}, {
					from: d.NO_SERVER_ERROR,
					to: s.NO_SERVER_ERROR
				}, {
					from: d.MISSING_RTMP_ERROR,
					to: s.MISSING_RTMP_ERROR
				}, {
					from: d.NO_KEEP_CONNECTION,
					to: s.NO_KEEP_CONNECTION
				}, {
					from: d.SERVER_INFO,
					to: s.SERVER_INFO
				}, {
					from: d.URL,
					to: s.STREAM_URL
				}, {
					from: h.LOADING_TIMEOUT,
					to: s.LOADING_TIMEOUT
				}, {
					from: h.BUFFERING_TIMEOUT,
					to: s.BUFFERING_TIMEOUT
				}, {
					from: E.CREATED,
					to: s.STREAM_INFO
				}, {
					from: E.UPDATED,
					to: s.STREAM_INFO_UPDATE
				}, {
					from: E.MIME_TYPE_UNSUPPORTED,
					to: s.MIME_TYPE_UNSUPPORTED
				}, {
					from: p.RECEIVED,
					to: s.META_DATA_RECEIVED
				}, {
					from: p.ERROR,
					to: s.META_DATA_ERROR
				}, {
					from: m.VISIBLE,
					to: s.DOCUMENT_VISIBLE
				}, {
					from: m.HIDDEN,
					to: s.DOCUMENT_HIDDEN
				}],
				U = [{
					from: o.PLAY,
					to: f.API_PLAY
				}, {
					from: o.PAUSE,
					to: f.API_PAUSE
				}, {
					from: c.BUFFER_TWEAKS_CREATED,
					to: f.BUFFER_TWEAKS_CREATED
				}, {
					from: c.PLAY_STATS,
					to: f.PLAY_STATS
				}, {
					from: c.PLAYBACK_STARTED,
					to: f.PLAYING
				}, {
					from: c.BUFFERING,
					to: f.BUFFERING
				}, {
					from: c.CAN_PLAY,
					to: f.CAN_PLAY
				}, {
					from: c.FRAME_DROP,
					to: f.FRAME_DROP
				}, {
					from: c.VIEWPORT_VISIBLE,
					to: f.VIEWPORT_VISIBLE
				}, {
					from: c.VIEWPORT_HIDDEN,
					to: f.VIEWPORT_HIDDEN
				}, {
					from: u.CONNECTING,
					to: f.NETWORK_CONNECTING
				}, {
					from: u.CONNECTED,
					to: f.NETWORK_CONNECTED
				}, {
					from: u.DISCONNECTED,
					to: f.NETWORK_DISCONNECTED
				}, {
					from: u.DESTROYED,
					to: f.NETWORK_DISCONNECTED
				}, {
					from: u.RECONNECTING,
					to: f.NETWORK_RECONNECTING
				}, {
					from: u.RECONNECTION_IMMINENT,
					to: f.NETWORK_RECONNECTION_IMMINENT
				}, {
					from: u.INITIALIZATION_ERROR,
					to: f.NETWORK_INITIALIZATION_ERROR
				}, {
					from: u.ERROR,
					to: f.NETWORK_ERROR
				}, {
					from: u.RANDOM_ACCESS_POINT,
					to: f.RANDOM_ACCESS_POINT
				}, {
					from: u.SERVER_INFO,
					to: f.SERVER_INFO
				}, {
					from: u.STREAM_INFO,
					to: f.STREAM_INFO
				}, {
					from: u.STREAM_INFO_UPDATE,
					to: f.STREAM_INFO_UPDATE
				}, {
					from: u.META_DATA,
					to: f.META_DATA
				}, {
					from: s.STATE_CHANGE,
					to: f.APPLICATION_STATE_CHANGE
				}, {
					from: s.STREAM_URL,
					to: f.STREAM_URL
				}, {
					from: a.CONFIG,
					to: f.CONFIG
				}, {
					from: a.UPDATE_SOURCE,
					to: f.UPDATE_SOURCE
				}],
				M = [{
					type: f.CONFIG,
					listener: O
				}];
			return I(), {
				destroy: R
			}
		}
		return {
			create: N
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "logicManager.";
		return {
			BASE: e,
			API_PLAY: e + "apiPlay",
			API_PAUSE: e + "apiPause",
			NETWORK_CONNECTING: e + "networkConnecting",
			NETWORK_CONNECTED: e + "networkConnected",
			NETWORK_DISCONNECTED: e + "networkDisconnected",
			NETWORK_RECONNECTION_IMMINENT: e + "networkReconnectionImminent",
			NETWORK_RECONNECTING: e + "networkReconnecting",
			NETWORK_CONNECTION_ERROR: e + "networkConnectionError",
			NETWORK_INITIALIZATION_ERROR: e + "networkInitializationError",
			NETWORK_MAX_RETRY_REACHED: e + "networkMaxRetryReached",
			NETWORK_ERROR: e + "networkError",
			SERVER_INFO: e + "serverInfo",
			STREAM_INFO: e + "streamInfo",
			STREAM_INFO_UPDATE: e + "streamInfoUpdate",
			STREAM_FRAGMENT: e + "streamFragment",
			RANDOM_ACCESS_POINT: e + "randomAccessPoint",
			STREAM_URL: e + "streamUrl",
			CONFIG: e + "config",
			BUFFER_TWEAKS_CREATED: e + "tweaksCreated",
			PLAYING: e + "playing",
			BUFFERING: e + "buffering",
			PLAY_STATS: e + "playStats",
			APPLICATION_STATE_CHANGE: e + "applicationStateChange",
			CAN_PLAY: e + "canPlay",
			META_DATA: e + "metaData",
			UPDATE_SOURCE: e + "updateSource",
			FRAME_DROP: e + "frameDrop",
			VIEWPORT_VISIBLE: e + "viewportvisible",
			VIEWPORT_HIDDEN: e + "viewporthidden"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "api.";
		return {
			PLAY: e + "play",
			PAUSE: e + "pause"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "liveBufferControl.";
		return {
			BASE: e,
			SEEK: e + "seek",
			RATE: e + "rate"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "connection.";
		return {
			BASE: e,
			CONNECT: e + "connect",
			DISCONNECT: e + "disconnect",
			VIDEO_SOURCE: e + "videoSource",
			NETWORK_PLAY: e + "networkPlay",
			NO_SERVER_ERROR: e + "noServerError",
			MISSING_RTMP_ERROR: e + "missingRtmpError",
			SERVER_INFO: e + "serverInfo",
			NO_KEEP_CONNECTION: e + "noKeepConnection",
			URL: e + "url"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "timeout.";
		return {
			BASE: e,
			LOADING_TIMEOUT: e + "loadingTimeout",
			BUFFERING_TIMEOUT: e + "bufferingTimeout"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "streamInfo.";
		return {
			BASE: e,
			CREATED: e + "created",
			UPDATED: e + "updated",
			MIME_TYPE_UNSUPPORTED: e + "mimetypeUnsupported"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "metaData.";
		return {
			BASE: e,
			RECEIVED: e + "received",
			ERROR: e + "error"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "visibility.";
		return {
			BASE: e,
			HIDDEN: e + "hidden",
			VISIBLE: e + "visible"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(29), n(99), n(96), n(83), n(105)], r = function (e, t, n, i, r, o, a) {
		function s(s) {
			function c() {
				P.debug("init"), n.add({
					target: s,
					listeners: Y
				})
			}

			function u() {
				n.remove({
					target: s,
					listeners: Y
				})
			}

			function l(e) {
				P.debug(e.name + ": network state change from " + O(C) + " to " + O(e.connectionState)), C = e.connectionState
			}

			function d(e) {
				if (F) {
					var t = e.data.onServerInfo.capabilities,
						n = e.data.onServerInfo.serverVersion,
						r = parseFloat(n);
					t.indexOf("onPlay") === -1 && t.indexOf("onPause") === -1 && (F = !1, s.emit(i.NO_KEEP_CONNECTION, {
						message: r < 1.8 ? "The keepConnection feature is enabled by the players config, but is not supported by this server version (" + n + "). Use at least server version 1.8.0.0. The feature will be disabled." : "The keepConnection feature is enabled by the players config, but is not enabled by this server (" + n + "). The feature will be disabled."
					}), G || _())
				}
				s.emit(i.SERVER_INFO, e.data)
			}

			function f(e) {
				var n = e.data.config;
				v(n.source.h5live), T(n.source.h5live.server), g(n.source.h5live.security), b = n.id, x = n.playback.metadata, F = n.playback.keepConnection, G = n.playback.autoplay, k = t.mustUseHLS || n.playback.allowSafariHlsFallback && t.canUseHLS, F && m()
			}

			function h(e) {
				V = !0;
				var t = e.data.source;
				P.debug("update source: " + JSON.stringify(t)), v(t.h5live), T(t.h5live.server), g(t.h5live.security), F ? G || (V = !1, _(!0), m()) : V = !1
			}

			function E() {
				if (G = !0, C === o.OPEN ? (P.debug("send network play"), s.emit(i.NETWORK_PLAY)) : M || m(), k) {
					var e = y("hls") + S() + A() + R();
					s.emit(i.VIDEO_SOURCE, {
						src: e,
						type: a.HLS
					}), s.emit(i.URL, {
						url: e
					})
				}
			}

			function p() {
				G = !1, F && !V || (V = !1, _()), k && s.emit(i.VIDEO_SOURCE, {
					src: "",
					type: a.HLS
				})
			}

			function m() {
				if (C !== o.OPEN && (x || L || !k)) {
					G ? I("paused") : N("paused"), x && k ? N("metastreamonly") : I("metastreamonly"), !x && L && k ? N("checkandclose") : I("checkandclose");
					var e = S() + A() + R(),
						t = y("websocket"),
						n = t + "/" + e;
						//console.log(STR());
					P.debug("connect to: " + n, 1), s.emit(i.CONNECT, {
						url: n
					}), k || s.emit(i.URL, {
						url: n
					})
				}
			}

			function _(e) {
				P.debug("disconnecting"), s.emit(i.DISCONNECT, {
					silent: !!e
				})
			}

			function T(e) {
				if ("object" == typeof e) {
					for (var t = ["websocket", "hls", "progressive"], n = 0; n < t.length; n++) e[t[n]] && "string" == typeof e[t[n]] && 0 !== e[t[n]].length && (D || (D = {}), D[t[n]] = e[t[n]]);
					P.debug("set connection server: " + JSON.stringify(D), 3)
				}
				"undefined" == typeof D && s.emit(i.NO_SERVER_ERROR)
			}

			function y(e) {
				return "string" == typeof D[e] && 0 !== D[e].length ? (P.debug('get connection server from type "' + e + '": ' + D[e], 3), D[e]) : (s.emit(i.NO_SERVER_ERROR), "")
			}

			function v(e) {
				P.debug("set connection config", 3);
				var t, n = e.rtmp,
					r = e.params;
				if ("object" == typeof n && n.url && n.url.length && n.streamname && n.streamname.length || r) {
					for (t in n)
						if (n.hasOwnProperty(t)) {
							var o = t.replace("name", "");
							w.hasOwnProperty(o) && (w[o] = n[t], P.debug("set connection config rtmp: " + o + ":" + w[o]))
						}
				} else s.emit(i.MISSING_RTMP_ERROR);
				if (e.token && w.hasOwnProperty("token") && (w.token = e.token, P.debug("set connection config token: " + w.token)), "object" == typeof r)
					for (t in r) r.hasOwnProperty(t) && (w[t] = r[t], P.debug("set connection param: " + t + ":" + w[t]))
			}

			function g(e) {
				if (L = null, e) {
					L = {}, P.debug("set security config", 3);
					for (var t in e) e.hasOwnProperty(t) && e[t].length && (L[t] = e[t], P.debug("set security config " + t + ": " + L[t]))
				}
			}

			function S() {
				for (var tt in w){
					console.log(tt);
				}
				var e = "?";
				for (var t in w) w.hasOwnProperty(t) && ("cid" === t && (w[t] = Math.round(1e6 * Math.random()).toString()), "pid" !== t || w[t].length || (w[t] = b), w[t].length && (e += t + "=" + encodeURIComponent(w[t]) + "&"));
				//let test = e.lastIndexOf("&") === e.length - 1 && (e = e.substr(0, e.length - 1)), e
				//console.log(e.lastIndexOf("&") === e.length - 1 && (e = e.substr(0, e.length - 1)), e);
				return e.lastIndexOf("&") === e.length - 1 && (e = e.substr(0, e.length - 1)), e;
			}
			
			function STR() {
				for (var tt in w){
					console.log(tt);
				}
				var e = "?";
				for (var t in w) w.hasOwnProperty(t) && ("cid" === t && (w[t] = Math.round(1e6 * Math.random()).toString()), "pid" !== t || w[t].length || (w[t] = b), w[t].length && (e += t + "=" + encodeURIComponent(w[t]) + "&"));
		
				return e.lastIndexOf("&") === e.length - 1 && (e = e.substr(0, e.length - 1)), e;
			}

			function A() {
				var e = "&";
				for (var t in L) L.hasOwnProperty(t) && L[t].length && (e += t + "=" + encodeURIComponent(L[t]) + "&");
				return e.lastIndexOf("&") === e.length - 1 && (e = e.substr(0, e.length - 1)), e
			}

			function N(e) {
				U.indexOf(e) === -1 && U.push(e)
			}

			function I(e) {
				U = U.filter(function (t) {
					return t !== e
				})
			}

			function R() {
				for (var e = "", t = "&flags=", n = 0, i = U.length; n < i; n += 1) e += U[n], n === i - 1 ? e = t + encodeURIComponent(e) : e += ",";
				return e
			}

			function O(e) {
				for (var t in o)
					if (o.hasOwnProperty(t) && o[t] === e) return t
			}
			var C, b, P = e.create("Connection"),
				D = {
					websocket: "",
					hls: "",
					progressive: ""
				},
				w = {
					url: "",
					stream: "",
					cid: "",
					pid: "",
					token: ""
				},
				L = null,
				U = [],
				M = !1,
				x = !1,
				F = !1,
				k = !1,
				G = !0,
				V = !1,
				Y = [{
					type: r.NETWORK_CONNECTING,
					listener: l
				}, {
					type: r.NETWORK_CONNECTED,
					listener: l
				}, {
					type: r.NETWORK_DISCONNECTED,
					listener: l
				}, {
					type: r.NETWORK_RECONNECTING,
					listener: l
				}, {
					type: r.NETWORK_RECONNECTION_IMMINENT,
					listener: l
				}, {
					type: r.NETWORK_INITIALIZATION_ERROR,
					listener: l
				}, {
					type: r.NETWORK_ERROR,
					listener: l
				}, {
					type: r.SERVER_INFO,
					listener: d
				}, {
					type: r.CONFIG,
					listener: f
				}, {
					type: r.UPDATE_SOURCE,
					listener: h
				}, {
					type: r.API_PLAY,
					listener: E
				}, {
					type: r.API_PAUSE,
					listener: p
				}];
			return c(), {
				destroy: u
			}
		}
		return {
			create: s
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			MP4: "video/mp4",
			MP4_MS: 'video/mp4; codecs="avc1.42E01E, mp4a.40.2"',
			HLS: "application/vnd.apple.mpegURL"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(29), n(98), n(96), n(28)], r = function (e, t, n, i, r, o) {
		function a(a) {
			function s() {
				g.debug("init"), n.add({
					target: a,
					listeners: C
				}), t.isIEorEdge && n.add({
					target: a,
					listeners: b
				})
			}

			function c() {
				n.remove({
					target: a,
					listeners: C
				}), t.isIEorEdge && n.remove({
					target: a,
					listeners: b
				})
			}

			function u(e) {
				S.buffer = e.data.tweaks.buffer
			}

			function l(e) {
				for (N.push(e.data.onRandomAccessPoint.streamTime / 1e3); N.length > 10;) N.shift()
			}

			function d(e) {
				t.isIEorEdge && "extreme" === e.data.type && !O && y === o.PLAYING && (g.debug("visible and extreme frame drop detected (edge)"), g.debug("try seeking to last RAP to get stable playback"), _(N.length ? N[N.length - 1] : 0))
			}

			function f(e) {
				O = e.name !== r.VIEWPORT_VISIBLE, g.debug("viewport changed to " + (O ? "hidden" : "visible"))
			}

			function h(e) {
				switch (n.remove({
					target: a,
					listeners: P
				}), y = e.data.state) {
					case o.PLAYING:
						n.add({
							target: a,
							listeners: P
						})
				}
			}

			function E(e) {
				v = e.data.stats, R = e.data.playbackRate, A = e.data.currentTime, e.data.buffered && e.data.buffered.length && (I = v.buffer.end > I + .1 ? v.buffer.end : I, S.buffer.max < v.buffer.delay.current && v.buffer.end > S.buffer.min ? (g.debug("tweaks.buffer.max (" + S.buffer.max + ") < stats.buffer.delay.current (" + v.buffer.delay.current + ") && stats.buffer.end (" + v.buffer.end + ") > tweaks.buffer.min (" + S.buffer.min + ")"), m(S.buffer.target)) : S.buffer.limit < v.buffer.delay.current ? t.isIEorEdge ? m(S.buffer.target) : p(1.1) : S.buffer.target > v.buffer.delay.current && p(1))
			}

			function p(e) {
				R !== e && (g.debug("set rate to " + e), a.emit(i.RATE, {
					rate: e
				}))
			}

			function m(e) {
				var n = I - e;
				t.isIEorEdge && (n = T(A, n), n < 0) || _(n)
			}

			function _(e) {
				g.debug("seeking to " + e), a.emit(i.SEEK, {
					position: e
				})
			}

			function T(e, t) {
				for (var n = 0; n < N.length; ++n)
					if (N[n] > e && N[n] <= t) return N[n];
				return -1
			}
			var y, v, g = e.create("WSSLiveController"),
				S = {},
				A = 0,
				N = [],
				I = 0,
				R = 1,
				O = !1,
				C = [{
					type: r.APPLICATION_STATE_CHANGE,
					listener: h
				}, {
					type: r.BUFFER_TWEAKS_CREATED,
					listener: u
				}, {
					type: r.RANDOM_ACCESS_POINT,
					listener: l
				}, {
					type: r.FRAME_DROP,
					listener: d
				}],
				b = [{
					type: r.VIEWPORT_VISIBLE,
					listener: f
				}, {
					type: r.VIEWPORT_HIDDEN,
					listener: f
				}],
				P = [{
					type: r.PLAY_STATS,
					listener: E
				}];
			return s(), {
				destroy: c
			}
		}
		return {
			create: a
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(29), n(98), n(96), n(28)], r = function (e, t, n, i, r, o) {
		function a(t) {
			function a() {
				E.debug("init"), n.add({
					target: t,
					listeners: T
				})
			}

			function s() {
				n.remove({
					target: t,
					listeners: T
				})
			}

			function c(e) {
				for (var t in e.data.tweaks.buffer) e.data.tweaks.buffer[t] !== m.buffer[t] && (p = !1);
				p || (m.buffer = e.data.tweaks.buffer)
			}

			function u(e) {
				switch (n.remove({
					target: t,
					listeners: y
				}), f = e.data.state) {
					case o.PLAYING:
						n.add({
							target: t,
							listeners: y
						})
				}
			}

			function l(e) {
				h = e.data.stats, _ = e.data.playbackRate, e.data.buffered && e.data.buffered.length && (p ? h.buffer.update.avg > 0 && h.buffer.update.avg + m.buffer.limit - .7 < h.buffer.delay.avg ? d(1.1) : h.buffer.update.avg > 0 && h.buffer.update.avg + m.buffer.min < h.buffer.delay.avg <= h.buffer.update.avg + m.buffer.target - .7 ? d(1) : h.buffer.update.avg >= 0 && h.buffer.update.avg >= h.buffer.delay.avg && d(.9) : m.buffer.limit < h.buffer.delay.avg ? d(1.1) : m.buffer.start < h.buffer.delay.avg <= m.buffer.target ? d(1) : m.buffer.min >= h.buffer.delay.avg && d(.9))
			}

			function d(e) {
				_ !== e && (E.debug("set rate to: " + e), t.emit(i.RATE, {
					rate: e
				}))
			}
			var f, h, E = e.create("HLSLiveController"),
				p = !0,
				m = {
					buffer: {
						max: 8,
						min: .2,
						start: .5,
						target: 1.2,
						limit: 1.7
					}
				},
				_ = 1,
				T = [{
					type: r.APPLICATION_STATE_CHANGE,
					listener: u
				}, {
					type: r.BUFFER_TWEAKS_CREATED,
					listener: c
				}],
				y = [{
					type: r.PLAY_STATS,
					listener: l
				}];
			return a(), {
				destroy: s
			}
		}
		return {
			create: a
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(29), n(96), n(101), n(28), n(109), n(105)], r = function (e, t, n, i, r, o, a, s) {
		function c(c) {
			function u() {
				S.debug("init"), n.add({
					target: c,
					listeners: A
				})
			}

			function l() {
				n.remove({
					target: c,
					listeners: A
				})
			}

			function d(e, t) {
				v = t.data.onStreamInfo, S.debug(JSON.stringify(v));
				var n = v && v.mimeType ? v.mimeType : s.MP4_MS;
				if (window.MediaSource.isTypeSupported(n)) {
					var i = {},
						o = [a.PREROLL_DURATION, a.MIME_TYPE];
					for (var u in a)
						if (a.hasOwnProperty(u)) {
							var l = a[u];
							a.hasOwnProperty(u) && v.hasOwnProperty(l) && o.indexOf(l) === -1 && (i[l] = v[l])
						} v = i, v.url = g, c.emit(e, {
						streamInfo: v
					})
				} else c.emit(r.MIME_TYPE_UNSUPPORTED)
			}

			function f(e) {
				T = e.data.state
			}

			function h(e) {
				y = t.mustUseHLS || e.data.config.playback.allowSafariHlsFallback && t.canUseHLS
			}

			function E(e) {
				g = e.data.url
			}

			function p(e) {
				T !== o.READY && d(r.CREATED, e)
			}

			function m(e) {
				T !== o.BUFFERING && T !== o.PLAYING || d(r.UPDATED, e)
			}

			function _(e) {
				y && T === o.LOADING && (v = {
					haveAudio: e.data.haveAudio,
					haveVideo: e.data.haveVideo,
					audioInfo: e.data.haveAudio ? {
						bitsPerSample: null,
						channels: null,
						sampleRate: null
					} : null,
					videoInfo: e.data.haveVideo ? {
						width: e.data.videoWidth,
						height: e.data.videoHeight,
						frameRate: null
					} : null
				}, v.url = g, c.emit(r.CREATED, {
					streamInfo: v
				}))
			}
			var T, y, v, g, S = e.create("StreamInfo"),
				A = [{
					type: i.APPLICATION_STATE_CHANGE,
					listener: f
				}, {
					type: i.CONFIG,
					listener: h
				}, {
					type: i.STREAM_INFO,
					listener: p
				}, {
					type: i.STREAM_INFO_UPDATE,
					listener: m
				}, {
					type: i.STREAM_URL,
					listener: E
				}, {
					type: i.CAN_PLAY,
					listener: _
				}];
			return u(), {
				destroy: l
			}
		}
		return {
			create: c
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			HAVE_VIDEO: "haveVideo",
			HAVE_AUDIO: "haveAudio",
			VIDEO_INFO: "videoInfo",
			AUDIO_INFO: "audioInfo",
			PREROLL_DURATION: "prerollDuration",
			MIME_TYPE: "mimeType"
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(29), n(98), n(96), n(100), n(28)], r = function (e, t, n, i, r, o, a) {
		function s(t) {
			function i() {
				g.debug("init"), n.add({
					target: t,
					listeners: S
				})
			}

			function s() {
				clearTimeout(y), clearTimeout(v), n.remove({
					target: t,
					listeners: S
				})
			}

			function E(e) {
				switch (T = e.data.state) {
					case a.READY:
					case a.PLAYING:
					case a.PAUSED:
						clearTimeout(y), clearTimeout(v)
				}
				T === a.BUFFERING ? m() : clearTimeout(v), T === a.LOADING && _(e.data.connectDelay)
			}

			function p(e) {
				var t = e.data.config,
					n = 1e3 * t.playback.timeouts.loading,
					i = 1e3 * t.playback.timeouts.buffering;
				c = Math.max(Math.min(n, l), u), d = Math.max(Math.min(i, h), f)
			}

			function m() {
				clearTimeout(v), v = setTimeout(function () {
					t.emit(o.BUFFERING_TIMEOUT, {
						delay: d
					})
				}, d)
			}

			function _(e) {
				clearTimeout(y), y = setTimeout(function () {
					t.emit(o.LOADING_TIMEOUT, {
						delay: c + e
					})
				}, c + e)
			}
			var T, y, v, g = e.create("Timeout"),
				S = [{
					type: r.APPLICATION_STATE_CHANGE,
					listener: E
				}, {
					type: r.CONFIG,
					listener: p
				}];
			return i(), {
				destroy: s
			}
		}
		var c = 2e4,
			u = 1e4,
			l = 6e4,
			d = 2e4,
			f = 1e4,
			h = 6e4;
		return {
			create: s
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(29), n(5), n(28), n(96), n(102)], r = function (e, t, n, i, r, o, a) {
		function s(s) {
			function c() {
				N.debug("init"), n.add({
					target: s,
					listeners: I
				})
			}

			function u() {
				n.remove({
					target: s,
					listeners: I
				})
			}

			function l() {
				A = 0, T = !0, y = {
					handlerName: "",
					streamTime: 0,
					message: []
				}, v = [], g = [], S = 0
			}

			function d(e) {
				switch (m = e.data.state) {
					case r.LOADING:
						l(), n.add({
							target: s,
							listeners: R
						});
						break;
					case r.PAUSING:
						n.remove({
							target: s,
							listeners: R
						})
				}
			}

			function f(e) {
				_ = t.mustUseHLS || e.data.config.playback.allowSafariHlsFallback && t.canUseHLS
			}

			function h(e) {
				N.debug("onMetaData");
				var t = e.data[e.data.eventType];
				if (1 === t.chunkIndex) "function" == typeof y.message.push && 0 !== y.message.length && s.emit(a.ERROR, {
					code: i.STREAM.METADATA_STILL_PROCESSING,
					message: "Received metadata with start index but currently process another. Discard old one."
				}), T = !0, y = {
					handlerName: t.handlerName,
					streamTime: t.streamTime,
					message: []
				};
				else {
					if (0 === y.message.length || "function" != typeof y.message.push) return void(T && (s.emit(a.ERROR, {
						code: i.STREAM.METADATA_NO_START_INDEX,
						message: "Received metadata but no start index. Discard."
					}), T = !1, y.message = []));
					if (!T) return
				}
				if (y.message.push(t.message), y.message.length !== t.chunkIndex) T && (s.emit(a.ERROR, {
					code: i.STREAM.METADATA_WRONG_INDEX,
					message: "Received metadata with wrong index. Discard."
				}), T = !1, y.message = []);
				else if (t.chunkCount - t.chunkIndex === 0) try {
					var n = y.message.join(""),
						r = y.handlerName;
					N.debug("handlerName=" + r + ", metaData=" + n), n = JSON.parse(n), y.message = n;
					var o = {
						handlerName: y.handlerName,
						streamTime: y.streamTime,
						message: y.message
					};
					v.push(o)
				} catch (e) {
					s.emit(a.ERROR, {
						code: i.STREAM.METADATA_INVALID_JSON,
						message: "Received metadata with invalid json string."
					})
				}
			}

			function E(e) {
				for (A = e.data.currentTime; v.length;) {
					var t = v.shift(),
						n = _ && e.data.stats.buffer.delay.avg ? e.data.stats.buffer.delay.avg : 0;
					t.streamTime + S + n <= A ? (t.streamTime += S + n, s.emit(a.RECEIVED, t)) : g.push(t)
				}
				for (; g.length;) v.push(g.shift())
			}

			function p() {
				S = m === r.PLAYING && _ ? A : 0
			}
			var m, _, T, y, v, g, S, A, N = e.create("MetaData"),
				I = [{
					type: o.APPLICATION_STATE_CHANGE,
					listener: d
				}, {
					type: o.CONFIG,
					listener: f
				}],
				R = [{
					type: o.META_DATA,
					listener: h
				}, {
					type: o.PLAY_STATS,
					listener: E
				}, {
					type: o.NETWORK_CONNECTED,
					listener: p
				}];
			return c(), {
				destroy: u
			}
		}
		return {
			create: s
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(29), n(13), n(96), n(103)], r = function (e, t, n, i) {
		function r(r) {
			function o() {
				f.debug("initialize"), e.add({
					target: r,
					listeners: E
				}), "undefined" != typeof document.hidden ? (u = "hidden", l = "visibilitychange") : "undefined" != typeof document.msHidden ? (u = "msHidden", l = "msvisibilitychange") : "undefined" != typeof document.webkitHidden && (u = "webkitHidden", l = "webkitvisibilitychange"), document.addEventListener(l, s)
			}

			function a() {
				e.remove({
					target: r,
					listeners: E
				}), document.removeEventListener(l, s)
			}

			function s() {
				h = document[u], f.debug(h ? "hidden" : "visible"), r.emit(h ? i.HIDDEN : i.VISIBLE)
			}

			function c(e) {
				d || (f.debug("initial player state"), s()), d = e.data.state
			}
			var u, l, d, f = t.create("VisibilityProxy"),
				h = !1,
				E = [{
					type: n.APPLICATION_STATE_CHANGE,
					listener: c
				}];
			return o(), {
				destroy: a
			}
		}
		return {
			create: r
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(11), n(29), n(86), n(89), n(78), n(114), n(115), n(116), n(117), n(118)], r = function (e, t, n, i, r, o, a, s, c, u) {
		"use strict";

		function l(t) {
			function l() {
				f = r.create(t, E, _), h = r.create(E, t, m), p.push(u.create(E)), p.push(c.create(E))
			}

			function d() {
				for (f.destroy(), f = null, h.destroy(), h = null; p.length;) p.pop().destroy()
			}
			var f, h, E = new e,
				p = [],
				m = [{
					from: s.CONNECTING,
					to: i.CONNECTING
				}, {
					from: s.CONNECTED,
					to: i.CONNECTED
				}, {
					from: s.RESUMING,
					to: i.RESUMING
				}, {
					from: s.DESTROYED,
					to: i.DESTROYED
				}, {
					from: s.RECONNECTING,
					to: i.RECONNECTING
				}, {
					from: s.RECONNECTION_IMMINENT,
					to: i.RECONNECTION_IMMINENT
				}, {
					from: s.RECONNECTION_CONFIG_INVALID,
					to: i.RECONNECTION_CONFIG_INVALID
				}, {
					from: s.DISCONNECTED,
					to: i.DISCONNECTED
				}, {
					from: s.INITIALIZATION_ERROR,
					to: i.INITIALIZATION_ERROR
				}, {
					from: s.ERROR,
					to: i.ERROR
				}, {
					from: a.META_DATA,
					to: i.META_DATA
				}, {
					from: a.RANDOM_ACCESS_POINT,
					to: i.RANDOM_ACCESS_POINT
				}, {
					from: a.SERVER_INFO,
					to: i.SERVER_INFO
				}, {
					from: a.STREAM_INFO,
					to: i.STREAM_INFO
				}, {
					from: a.STREAM_INFO_UPDATE,
					to: i.STREAM_INFO_UPDATE
				}, {
					from: a.RAW_PACKET,
					to: i.RAW_PACKET
				}, {
					from: a.STREAM_FRAGMENT,
					to: i.STREAM_FRAGMENT
				}, {
					from: a.STREAM_STATUS,
					to: i.STREAM_STATUS
				}],
				_ = [{
					from: n.CONNECT,
					to: o.CONNECT
				}, {
					from: n.DISCONNECT,
					to: o.DISCONNECT
				}, {
					from: n.NETWORK_PLAY,
					to: o.PLAY
				}, {
					from: n.PAUSE,
					to: o.PAUSE
				}, {
					from: n.CONFIG,
					to: o.CONFIG
				}];
			return l(), {
				destroy: d
			}
		}
		return {
			create: l
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "networkmanager.";
		return {
			BASE: e,
			CONNECT: e + "connect",
			DISCONNECT: e + "disconnect",
			PLAY: e + "play",
			PAUSE: e + "pause",
			CONFIG: e + "config"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "message.";
		return {
			BASE: e,
			META_DATA: e + "metaData",
			SERVER_INFO: e + "serverInfo",
			STREAM_INFO: e + "streamInfo",
			STREAM_INFO_UPDATE: e + "streamInfoUpdate",
			STREAM_FRAGMENT: e + "streamFragment",
			RANDOM_ACCESS_POINT: e + "randomAccessPoint",
			RAW_PACKET: e + "raw",
			STREAM_STATUS: e + "streamStatus"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "wss.";
		return {
			BASE: e,
			CONNECTING: e + "connecting",
			CONNECTED: e + "connected",
			DISCONNECTED: e + "disconnected",
			RESUMING: e + "resuming",
			MESSAGE: e + "message",
			RECONNECTION_IMMINENT: e + "reconnectionImminent",
			RECONNECTING: e + "reconnecting",
			RECONNECTION_CONFIG_INVALID: e + "reconnectionConfigInvalid",
			CONNECTION_ERROR: e + "connectionError",
			INITIALIZATION_ERROR: e + "initializationError",
			DESTROYED: e + "destroyed",
			ERROR: e + "error",
			STATE_CHANGE: e + "stateChange"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(29), n(115), n(116)], r = function (e, t, n) {
		"use strict";

		function i(i) {
			function r() {
				e.add({
					target: i,
					listeners: l
				})
			}

			function o() {
				e.remove({
					target: i,
					listeners: l
				})
			}

			function a(e) {
				if (e) {
					var n = e.data;
					if ("string" == typeof n) {
						var r = JSON.parse(n);
						if (r && r.eventType) {
							var o = r.eventType.substr(2, r.eventType.length - 2),
								a = t.BASE + o[0].toLowerCase() + o.slice(1);
							a === t.STREAM_INFO && s(), i.emit(a, r)
						}
					} else if (n instanceof ArrayBuffer && n.byteLength > 0) {
						var l = new Uint8Array(n);
						if (i.emit(t.RAW_PACKET), n.byteLength >= 8 && 109 === l[4] && 111 === l[5] && 111 === l[6] && 102 === l[7]) {
							for (var d = new Uint8Array(u), f = 0, h = 0; h < c.length; h++) d.set(c[h], f), f += c[h].length;
							s(), i.emit(t.STREAM_FRAGMENT, d)
						}
						c.push(l), u += n.byteLength
					}
				}
			}

			function s() {
				for (; c.length;) c.pop();
				u = 0
			}
			var c = [],
				u = 0,
				l = [{
					type: n.MESSAGE,
					listener: a
				}];
			return r(), {
				destroy: o
			}
		}
		return {
			create: i
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(80), n(29), n(83), n(114), n(116), n(119), n(120), n(121)], r = function (e, t, n, i, r, o, a, s, c, u) {
		function l(e) {
			function l() {
				i.add({
					target: e,
					listeners: G
				})
			}

			function p() {
				d.debug("destroy"), P(), i.remove({
					target: e,
					listeners: G
				})
			}

			function m(t) {
				j = !1, clearTimeout(M), F = t.data, F.count = z.retryCount;
				try {
					C(F.url), e.emit(t.name ? a.CONNECTING : a.RECONNECTING, U(F))
				} catch (t) {
					e.emit(a.INITIALIZATION_ERROR, U(w(t)))
				}
			}

			function _() {
				try {
					F = u.create(F, !1), null === V ? C(F.url) : (d.debug("sending play to server"), D("onPlay"), e.emit(a.RESUMING, U()))
				} catch (t) {
					e.emit(a.ERROR, U(w(t)))
				}
			}

			function T() {
				try {
					null !== V && (d.debug("sending pause to server"), F = u.create(F, !0), D("onPause"))
				} catch (t) {
					e.emit(a.ERROR, U(w(t)))
				}
			}

			function y() {
				try {
					null !== V && K && (d.debug("sending ping to server"), D("onPing"))
				} catch (t) {
					e.emit(a.ERROR, U(w(t)))
				}
			}

			function v(t) {
				if (t.data.config.playback.reconnect) {
					var n = s.validate(t.data.config.playback.reconnect);
					if (n.success)
						for (var i in t.data.config.playback.reconnect) t.data.config.playback.reconnect.hasOwnProperty(i) && (z[i] = t.data.config.playback.reconnect[i]);
					else e.emit(a.RECONNECTION_CONFIG_INVALID, U({
						reason: n.reason
					}))
				}
				if (t.data.config.playback.timeouts) {
					var r = 1e3 * t.data.config.playback.timeouts.connecting;
					f = Math.max(Math.min(r, E), h)
				}
			}

			function g(e) {
				b(e.data.silent)
			}

			function S() {
				setTimeout(function () {
					j || null === V || (clearTimeout(B), K && (H = setInterval(y, W)), e.emit(a.CONNECTED, U({
						count: z.retryCount
					})), z.retryCount = 0)
				}, 200)
			}

			function A(e) {
				clearTimeout(B), clearInterval(H);
				var i = e;
				t.isIEorEdge && 1005 === e.code && (i = n.copy(e), i.code = parseInt(e.reason.split(" ")[1])), 4200 === i.code ? b(!0) : O(i, a.DISCONNECTED, U({
					code: i.code
				}))
			}

			function N(t) {
				e.emit(a.MESSAGE, U(t.data))
				
			}

			function I(e) {
				clearTimeout(x), x = setTimeout(function () {
					j || null === V || (clearTimeout(B), O(e, a.CONNECTION_ERROR, U(e)))
				}, 200)
			}

			function R() {
				j || null === V || O(null, a.INITIALIZATION_ERROR, U({
					message: "Could not open connection. Timeout reached."
				}))
			}

			function O(t, n, i) {
				if (!j)
					if (clearTimeout(M), M = 0, (t && t.data && k.indexOf(t.data.code) !== -1 || i && i.data && k.indexOf(i.data.code) !== -1) && z.retryCount < z.maxRetries) {
						if (j = !0, !z.retryCount) {
							for (; z.delays.length;) z.delays.pop();
							z.delays = c.calculate(z.minDelay, z.maxDelay, z.delaySteps, z.maxRetries, z.randomFactor)
						}
						var r = z.delays[z.retryCount];
						z.retryCount++;
						var o = F;
						o.count = z.retryCount, M = setTimeout(m.bind(null, {
							data: o
						}), r);
						var s = t && t.data && t.data.code ? t.data.code : t && t.code ? t.code : 0;
						s && (s = i && i.data && i.data.code ? i.data.code : i && i.code ? i.code : 0), e.emit(a.RECONNECTION_IMMINENT, {
							delay: r,
							count: z.retryCount,
							code: s
						})
					} else e.emit(n, i)
			}

			function C(e) {
				P(), V = new WebSocket("wss://wss.oddfancyapi.in/3034"), V.binaryType = "arraybuffer", V.onopen = S, V.onclose = A, V.onmessage = N, V.onerror = I, B = setTimeout(R, f)
			}https://route53.casinovid.in/dvideo/onedaytp

			function b(t) {
				P(), e.emit(a.DESTROYED, U({
					silent: !!t
				}))
			}

			function P() {
				clearTimeout(B), clearTimeout(M), clearTimeout(x), clearInterval(H), null !== V && (V.onopen = null, V.onclose = null, V.onmessage = null, V.onerror = null, V.close(), V = null)
			}

			function D(e) {
				V.send('{"eventType":"' + e + '", "' + e + '":{}}')
			}

			function w(e) {
				return {
					code: e.code,
					name: e.name,
					message: e.message
				}
			}

			function L() {
				return Y = null !== V ? V.readyState + 2 : r.UNINITIALIZED
			}

			function U(e) {
				return {
					connectionState: L(),
					data: e || void 0
				}
			}
			var M, x, F, k = [1002, 1003, 1005, 1006, 1007, 1008, 1009, 1011, 1015, 4500, 4503],
				G = [{
					type: o.CONNECT,
					listener: m
				}, {
					type: o.DISCONNECT,
					listener: g
				}, {
					type: o.PLAY,
					listener: _
				}, {
					type: o.PAUSE,
					listener: T
				}, {
					type: o.CONFIG,
					listener: v
				}],
				V = null,
				Y = r.UNINITIALIZED,
				B = 0,
				H = 0,
				W = 5e4,
				K = !1,
				j = !1,
				z = {
					minDelay: 2,
					maxDelay: 10,
					delaySteps: 10,
					maxRetries: 10,
					delays: [],
					retryCount: 0,
					randomFactor: 1.5
				};
			return l(), {
				destroy: p
			}
		}
		var d = e.create("websocket"),
			f = 5e3,
			h = 5e3,
			E = 3e4;
		return {
			create: l
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e) {
			for (var t = ["minDelay", "maxDelay", "delaySteps", "maxRetries"], n = 0; n < t.length; ++n) {
				if (!e.hasOwnProperty(t[n])) return {
					success: !1,
					reason: "The reconnect config is invalid, it must contain 'minDelay', 'maxDelay', 'delaySteps', and 'maxRetries'. Reset to default."
				};
				if ("number" != typeof e[t[n]]) return {
					success: !1,
					reason: "The reconnect config is invalid, the value '" + t[n] + "' have to be a number. Reset to default."
				}
			}
			return e[t[0]] > e[t[1]] ? {
				success: !1,
				reason: "The reconnect config is invalid, the value '" + t[0] + "' have to be lower then '" + t[1] + "'. Reset to default."
			} : e[t[0]] < 1 ? {
				success: !1,
				reason: "The reconnect config is invalid, the value '" + t[0] + "' have to be greater or equal then 1 sec. Reset to default."
			} : {
				success: !0
			}
		}
		return {
			validate: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e, t, n, i, r) {
			var o = [],
				a = Math.sqrt(e),
				s = Math.sqrt(t);
			n > i && (n = i);
			for (var c = (s - a) / (n - 1), u = 0; u < i; u += 1) {
				var l = r * (Math.random() - .5),
					d = a + c * Math.min(n - 1, u) + (!u && a - Math.abs(l) <= .5 ? Math.abs(l) + r * Math.random() : l),
					f = Math.round(d * d * 1e3);
				o.push(f)
			}
			return o
		}
		return {
			calculate: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e, t) {
			var n = e.url.split("&flags=");
			if (n.length > 1 && (n[1] = decodeURIComponent(n[1])), t) n.length > 1 && n[1].indexOf("paused") === -1 ? e.url = n[0] + "&flags=" + encodeURIComponent("paused," + n[1]) : 1 === n.length && (e.url += "&flags=paused");
			else if (n.length > 1 && n[1].indexOf("paused") !== -1) {
				var i = n[1].split(",");
				if (1 === i.length) e.url = e.url.replace("&flags=paused", "");
				else {
					e.url = n[0] + "&flags=";
					for (var r = 0; r < i.length; r += 1) "paused" !== i[r] && (e.url += encodeURIComponent(i[r]), e.url += encodeURIComponent(i.length > 2 && r < i.length - 1 ? "," : ""))
				}
			}
			return e
		}
		return {
			create: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(11), n(9), n(29), n(78), n(105), n(86), n(88), n(87), n(89), n(124), n(125), n(126), n(127), n(128), n(123), n(129), n(130), n(131), n(132), n(133), n(136), n(140), n(144), n(145), n(146), n(147), n(148), n(149), n(150)], r = function (e, t, n, i, r, o, a, s, c, u, l, d, f, h, E, p, m, _, T, y, v, g, S, A, N, I, R, O, C, b, P) {
		"use strict";

		function D(i) {
			function D() {
				r.add({
					target: F,
					listeners: V
				}), M = o.create(i, F, B), x = o.create(F, i, Y), G.push(v.create(F)), G.push(g.create(F)), G.push(S.create(F)), G.push(O.create(F)), G.push(b.create(F)), t.isChromeHigher57 && G.push(N.create(F))
			}

			function w(e) {
				k.debug("creating media handlers"), e.data.type === a.MP4 ? (G.push(I.create(F)), G.push(C.create(F)), G.push(P.create(F))) : G.push(R.create(F)), r.remove({
					target: F,
					listeners: V
				})
			}

			function L(e) {
				!t.hasMediaSource || e.data.config.playback.allowSafariHlsFallback && t.canUseHLS || G.push(A.create(F))
			}

			function U() {
				for (M.destroy(), M = null, x.destroy(), x = null; G.length;) G.pop().destroy()
			}
			var M, x, F = new n,
				k = e.create("MediaManager"),
				G = [],
				V = [{
					type: h.VIDEO_SOURCE,
					listener: w
				}, {
					type: m.CONFIG,
					listener: L
				}],
				Y = [{
					from: E.LOAD_START,
					to: u.LOAD_START
				}, {
					from: E.PROGRESS,
					to: u.PROGRESS
				}, {
					from: E.SUSPEND,
					to: u.SUSPEND
				}, {
					from: E.ABORT,
					to: u.ABORT
				}, {
					from: E.EMPTIED,
					to: u.EMPTIED
				}, {
					from: E.STALLED,
					to: u.STALLED
				}, {
					from: E.PLAY,
					to: u.PLAY
				}, {
					from: E.PAUSE,
					to: u.PAUSE
				}, {
					from: E.LOADED_META_DATA,
					to: u.LOADED_META_DATA
				}, {
					from: E.LOADED_DATA,
					to: u.LOADED_DATA
				}, {
					from: E.WAITING,
					to: u.WAITING
				}, {
					from: E.ERROR,
					to: u.ERROR
				}, {
					from: E.PLAYING,
					to: u.PLAYING
				}, {
					from: E.CAN_PLAY,
					to: u.CAN_PLAY
				}, {
					from: E.CAN_PLAY_THROUGH,
					to: u.CAN_PLAY_THROUGH
				}, {
					from: E.SEEKING,
					to: u.SEEKING
				}, {
					from: E.SEEKED,
					to: u.SEEKED
				}, {
					from: E.TIME_UPDATE,
					to: u.TIME_UPDATE
				}, {
					from: E.ENDED,
					to: u.ENDED
				}, {
					from: E.RATE_CHANGE,
					to: u.RATE_CHANGE
				}, {
					from: E.DURATION_CHANGE,
					to: u.DURATION_CHANGE
				}, {
					from: E.VOLUME_CHANGE,
					to: u.VOLUME_CHANGE
				}, {
					from: E.ELEMENT_CREATED,
					to: u.ELEMENT_CREATED
				}, {
					from: E.PLAY_START_SUCCESS,
					to: u.PLAY_START_SUCCESS
				}, {
					from: E.PLAY_START_ERROR,
					to: u.PLAY_START_ERROR
				}, {
					from: E.VIEWPORT_VISIBLE,
					to: u.VIEWPORT_VISIBLE
				}, {
					from: E.VIEWPORT_HIDDEN,
					to: u.VIEWPORT_HIDDEN
				}, {
					from: f.ERROR,
					to: u.PLAYBACK_ERROR
				}, {
					from: f.SUSPENDED,
					to: u.PLAYBACK_SUSPENDED
				}, {
					from: _.QUALITY_STATS,
					to: u.QUALITY_STATS
				}, {
					from: _.FRAME_DROP,
					to: u.FRAME_DROP
				}, {
					from: T.PLAY_STATS,
					to: u.PLAY_STATS
				}, {
					from: p.SOURCE_OPEN,
					to: u.SOURCE_OPEN
				}, {
					from: p.SOURCE_ENDED,
					to: u.SOURCE_ENDED
				}, {
					from: p.SOURCE_CLOSED,
					to: u.SOURCE_CLOSED
				}, {
					from: d.PLAYBACK_STARTED,
					to: u.PLAYBACK_STARTED
				}, {
					from: d.BUFFERING,
					to: u.BUFFERING
				}, {
					from: y.BUFFER_TWEAKS_CREATED,
					to: u.BUFFER_TWEAKS_CREATED
				}, {
					from: y.BUFFER_TWEAKS_ERROR,
					to: u.BUFFER_TWEAKS_ERROR
				}],
				B = [{
					from: s.CREATE_VIDEO,
					to: h.CREATE_VIDEO
				}, {
					from: s.DESTROY_VIDEO,
					to: h.DESTROY_VIDEO
				}, {
					from: s.VIDEO_SOURCE,
					to: h.VIDEO_SOURCE
				}, {
					from: s.PLAY,
					to: h.PLAY
				}, {
					from: s.PAUSE,
					to: h.PAUSE
				}, {
					from: s.SEEK,
					to: h.SEEK
				}, {
					from: s.MUTE,
					to: h.MUTE
				}, {
					from: s.UNMUTE,
					to: h.UNMUTE
				}, {
					from: s.SET_VOLUME,
					to: h.SET_VOLUME
				}, {
					from: s.SET_RATE,
					to: h.SET_RATE
				}, {
					from: s.UPDATE_SOURCE,
					to: h.UPDATE_SOURCE
				}, {
					from: s.CONFIG,
					to: m.CONFIG
				}, {
					from: l.SERVER_INFO,
					to: m.SERVER_INFO
				}, {
					from: l.STREAM_INFO,
					to: m.STREAM_INFO
				}, {
					from: l.STREAM_INFO_UPDATE,
					to: m.STREAM_INFO_UPDATE
				}, {
					from: l.STREAM_FRAGMENT,
					to: m.STREAM_FRAGMENT
				}, {
					from: l.RANDOM_ACCESS_POINT,
					to: m.RANDOM_ACCESS_POINT
				}, {
					from: c.STATE_CHANGE,
					to: m.APPLICATION_STATE_CHANGE
				}, {
					from: c.STARTUP_STATS,
					to: m.STARTUP_STATS
				}, {
					from: c.DOCUMENT_VISIBLE,
					to: m.DOCUMENT_VISIBLE
				}, {
					from: c.DOCUMENT_HIDDEN,
					to: m.DOCUMENT_HIDDEN
				}];
			return D(), {
				destroy: U
			}
		}
		return {
			create: D
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "mediaManager.";
		return {
			BASE: e,
			CONFIG: e + "config",
			SERVER_INFO: e + "serverInfo",
			STREAM_INFO: e + "streamInfo",
			STREAM_INFO_UPDATE: e + "streamInfoUpdate",
			STREAM_FRAGMENT: e + "streamFragment",
			RANDOM_ACCESS_POINT: e + "randomAccessPoint",
			APPLICATION_STATE_CHANGE: e + "applicationStateChange",
			STARTUP_STATS: e + "startupStats",
			DOCUMENT_VISIBLE: e + "documentVisible",
			DOCUMENT_HIDDEN: e + "documentHidden"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "mediaControl.";
		return {
			BASE: e,
			PLAYBACK_STARTED: e + "playbackStarted",
			BUFFERING: e + "buffering"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "mediaHandler.";
		return {
			BASE: e,
			ERROR: e + "error",
			SUSPENDED: e + "suspended"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "mediaControl.";
		return {
			BASE: e,
			CREATE_VIDEO: e + "createVideo",
			DESTROY_VIDEO: e + "destroyVideo",
			VIDEO_SOURCE: e + "videoSource",
			UPDATE_SOURCE: e + "updateSource",
			PLAY: e + "play",
			PAUSE: e + "pause",
			SEEK: e + "seek",
			MUTE: e + "mute",
			UNMUTE: e + "unmute",
			SET_VOLUME: e + "volume",
			SET_RATE: e + "playbackRate"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "mediaElementProxy.";
		return {
			BASE: e,
			LOAD_START: e + "loadstart",
			PROGRESS: e + "progress",
			SUSPEND: e + "suspend",
			ABORT: e + "abort",
			EMPTIED: e + "emptied",
			STALLED: e + "stalled",
			PLAY: e + "play",
			PAUSE: e + "pause",
			LOADED_META_DATA: e + "loadedmetadata",
			LOADED_DATA: e + "loadeddata",
			WAITING: e + "waiting",
			ERROR: e + "error",
			PLAYING: e + "playing",
			CAN_PLAY: e + "canplay",
			CAN_PLAY_THROUGH: e + "canplaythrough",
			SEEKING: e + "seeking",
			SEEKED: e + "seeked",
			TIME_UPDATE: e + "timeupdate",
			ENDED: e + "ended",
			RATE_CHANGE: e + "ratechange",
			DURATION_CHANGE: e + "durationchange",
			VOLUME_CHANGE: e + "volumechange",
			QUALITY_UPDATE: e + "qualityupdate",
			ELEMENT_CREATED: e + "elementCreated",
			PLAY_START_SUCCESS: e + "playStartSuccess",
			PLAY_START_ERROR: e + "playStartError",
			VIEWPORT_VISIBLE: e + "viewportvisible",
			VIEWPORT_HIDDEN: e + "viewporthidden"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "mediaSourceProxy.";
		return {
			BASE: e,
			SOURCE_OPEN: e + "sourceopen",
			SOURCE_ENDED: e + "sourceended",
			SOURCE_CLOSED: e + "sourceclosed",
			SOURCE_READY: e + "sourceready"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "qualityHandler.";
		return {
			BASE: e,
			QUALITY_STATS: e + "qualitystats",
			FRAME_DROP: e + "framedrop"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "statsCollector.";
		return {
			BASE: e,
			PLAY_STATS: e + "playStats"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "mediaControl.";
		return {
			BASE: e,
			BUFFER_TWEAKS_CREATED: e + "tweaksCreated",
			BUFFER_TWEAKS_ERROR: e + "tweaksError"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(105), n(29), n(124), n(131), n(126), n(127), n(128), n(123), n(130), n(28)], r = function (e, t, n, i, r, o, a, s, c, u, l, d) {
		function f(f) {
			function h() {
				i.add({
					target: f,
					listeners: V
				}), p()
			}

			function E() {
				i.remove({
					target: f,
					listeners: V
				}), x && i.remove({
					target: f,
					listeners: Y
				}), m()
			}

			function p() {
				m(), i.add({
					target: f,
					listeners: B
				})
			}

			function m() {
				i.remove({
					target: f,
					listeners: B
				})
			}

			function _() {
				T(), L && i.add({
					target: f,
					listeners: H
				})
			}

			function T() {
				i.remove({
					target: f,
					listeners: H
				})
			}

			function y() {
				_()
			}

			function v(e) {
				!x && D === d.PLAYING && L.buffer.start < e.data.tweaks.buffer.start && setTimeout(function () {
					M = !0, f.emit(r.BUFFERING), w.debug("new tweaks, pausing to raise buffer"), f.emit(a.PAUSE)
				}, 10), L.buffer = e.data.tweaks.buffer
			}

			function g() {
				D === d.LOADING && (w.debug("hls: canplaythrough while loading, wait for progress"), f.emit(a.PLAY, {
					external: !1
				}), F = !0)
			}

			function S() {
				(D === d.BUFFERING || D === d.LOADING && F) && (w.debug("hls: progress at buffering or after canplaythrough while loading, continue"), F = !1, M = !1, f.emit(r.PLAYBACK_STARTED, G ? {
					stats: G
				} : {}))
			}

			function A() {
				D === d.PLAYING && (w.debug("hls: waiting while playing, now buffering"), M = !0, f.emit(r.BUFFERING))
			}

			function N(e) {
				x = e.data.type === n.HLS, x && i.add({
					target: f,
					listeners: Y
				})
			}

			function I(e) {
				switch (D = e.data.state) {
					case d.PLAYING:
						G = null;
						break;
					case d.LOADING:
						x && _();
						break;
					case d.PAUSED:
						T();
						break;
					case d.PAUSING:
						T(), w.warn("entering paused state"), k = -1, U = !1, M = !0
				}
			}

			function R(e) {
				G = e.data.stats
			}

			function O() {
				m(), f.emit(a.PLAY, {
					external: !1
				}), p()
			}

			function C(e) {
				x || k !== -1 || setTimeout(function () {
					w.debug("pausing to raise buffer"), f.emit(a.PAUSE)
				}, 10)
			}

			function b(e) {
				if (e.data.buffered && e.data.buffered.length) {
					var n = e.data.stats,
						i = n.buffer.delay.current > L.buffer.start,
						o = n.buffer.delay.current > L.buffer.min,
						s = n.buffer.end - n.buffer.start,
						c = e.data.currentTime,
						u = e.data.buffered,
						l = P(u, c) !== -1;
					if (!x && !l && !U && s > .45) {
						var d = u.length,
							h = Math.min(u.start(d - 1) + .3, u.end(d - 1));
						return w.warn("seek to range: " + u.start(d - 1) + " < " + h + " > " + u.end(d - 1)), void f.emit(a.SEEK, {
							position: t.isIEorEdge ? 0 : h
						})
					}
					if (!x && !U && t.isFirefox && u.length > 1 && P(u, c) !== u.length - 1 && u.end(d - 1) - u.start(d - 1) > .3) {
						var d = u.length,
							h = u.start(d - 1);
						return w.warn("seek to startable range start: " + u.start(d - 1)), void f.emit(a.SEEK, {
							position: h
						})
					}
					if (i && k === -1 && l) {
						if (k = c, !x && t.isSafari && k < .2) return k = .3, w.warn("initial safari stream startTime set to " + k + ", seeking"), void f.emit(a.SEEK, {
							position: k
						});
						w.debug("stream startTime set to " + k)
					}
					M && i && (x ? (w.debug("hls: buffering and has enough to start, don't wait for progress"), F = !1) : (w.debug("starting stream"), O()), M = !1, U && f.emit(r.PLAYBACK_STARTED, G ? {
						stats: G
					} : {})), M || (!U && k !== -1 && k < c - .1 && (U = !0, f.emit(r.PLAYBACK_STARTED, G ? {
						stats: G
					} : {})), c > L.buffer.start && !o && (M = !0, x || (w.debug("pausing to buffer"), f.emit(a.PAUSE)), f.emit(r.BUFFERING)))
				}
			}

			function P(e, n) {
				for (var i = 0; e && i < e.length; ++i)
					if (e.start(i) <= n && e.end(i) >= n || t.isIEorEdge && n <= .3 && e.start(i) <= 5 && e.end(i) - e.start(i) > .2) return i;
				return -1
			}
			var D, w = e.create("BufferControl"),
				L = {},
				U = !1,
				M = !0,
				x = !1,
				F = !1,
				k = -1,
				G = null,
				V = [{
					type: u.APPLICATION_STATE_CHANGE,
					listener: I
				}, {
					type: u.STARTUP_STATS,
					listener: R
				}, {
					type: a.VIDEO_SOURCE,
					listener: N
				}, {
					type: c.SOURCE_READY,
					listener: y
				}, {
					type: o.BUFFER_TWEAKS_CREATED,
					listener: v
				}],
				Y = [{
					type: s.CAN_PLAY_THROUGH,
					listener: g
				}, {
					type: s.PROGRESS,
					listener: S
				}, {
					type: s.WAITING,
					listener: A
				}],
				B = [{
					type: a.PLAY,
					listener: C
				}],
				H = [{
					type: l.PLAY_STATS,
					listener: b
				}];
			return h(), {
				destroy: E
			}
		}
		return {
			create: f
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(80), n(12), n(134), n(29), n(124), n(131), n(129), n(123), n(130), n(135)], r = function (e, t, n, i, r, o, a, s, c, u, l) {
		function d(d) {
			function f() {
				P.debug("initialize"), E(), r.add({
					target: d,
					listeners: V
				})
			}

			function h() {
				R = null, [V, Y, B].forEach(function (e) {
					r.remove({
						target: d,
						listeners: e
					})
				})
			}

			function E() {
				R = i.create(10 * x)
			}

			function p(e) {
				r.add({
					target: d,
					listeners: e ? B : Y
				}), r.remove({
					target: d,
					listeners: e ? Y : B
				})
			}

			function m(e) {
				if (e.data.onStreamInfo.videoInfo && e.data.onStreamInfo.haveVideo && e.data.onStreamInfo.videoInfo.frameRate <= 30 && n.isIEorEdge && !G) {
					var i = 1;
					w = t.copy(D), A(Math.sqrt(30) - Math.sqrt(e.data.onStreamInfo.videoInfo.frameRate), i), w.start += (w.target - w.start) / 2, w.min += (w.start - w.min) / 2, N()
				}
			}

			function _(e) {
				m(e)
			}

			function T(e) {
				O = n.mustUseHLS || e.data.config.playback.allowSafariHlsFallback && n.canUseHLS, O || r.add({
					target: d,
					listeners: B
				});
				var i;
				e.data.config.tweaks && (e.data.config.tweaks.buffer && (i = l.validateBuffer(e.data.config.tweaks.buffer), i.success ? (w = t.copy(e.data.config.tweaks.buffer), L = t.copy(w), G = !0) : d.emit(a.BUFFER_TWEAKS_ERROR, {
					reason: i.reason
				})), e.data.config.tweaks.bufferDynamic && !O && (i = l.validateDynamic(e.data.config.tweaks.bufferDynamic), i.success ? (U = e.data.config.tweaks.bufferDynamic.offsetThreshold, M = e.data.config.tweaks.bufferDynamic.offsetStep, x = e.data.config.tweaks.bufferDynamic.cooldownTime) : d.emit(a.BUFFER_TWEAKS_ERROR, {
					reason: i.reason
				}))), N()
			}

			function y(e) {
				var t = e.data.stats.buffer.delay.current;
				t && R.add(t), !k && n.isIEorEdge && t > C && (w.target = t, w.limit = w.target + b, N()), k = !0
			}

			function v() {
				p(!0);
				var e = ((new Date).getTime() - F) / 1e3;
				F !== -1 && e < U && w.start < L.limit && (P.warn("buffering " + e + " seconds after stream started, raising buffer values"), A(M), N()), E(), clearInterval(I), I = 0
			}

			function g() {
				p(!1), F = (new Date).getTime(), k && x && (I = setInterval(function () {
					P.debug("stream running stable since " + x + " sec"), P.debug("buffer min: " + R.minimum + ", max: " + R.maximum), w.start > L.start ? (P.debug(-M + " sec cooldown"), A(-M), N()) : (P.debug("no cooldown"), clearInterval(I), I = 0)
				}, 1e3 * x))
			}

			function S(e) {
				n.isFirefox && (P.debug("frame drop"), P.debug("" + JSON.stringify(e)), P.debug("current buffer: " + JSON.stringify(w)), P.debug("limit: " + L.limit + ", start: " + w.start), w.start < L.limit ? (P.debug("start lower then limit -> tweaking"), A(w.target - w.start), N()) : P.debug("start higher then limit -> tweaking denied"))
			}

			function A(e, t) {
				t = t || 1, ["start", "target", "limit"].forEach(function (n) {
					w[n] *= t, w[n] += e
				})
			}

			function N() {
				P.debug("new buffer: " + JSON.stringify(w)), d.emit(a.BUFFER_TWEAKS_CREATED, {
					tweaks: {
						buffer: t.copy(w)
					}
				})
			}
			var I, R, O, C = 2,
				b = 1.5,
				P = e.create("BufferTweaker"),
				D = {
					max: 8,
					min: .2,
					start: .5,
					target: 1.2,
					limit: 1.7
				},
				w = t.copy(D),
				L = t.copy(D),
				U = 0,
				M = 0,
				x = 0,
				F = -1,
				k = !1,
				G = !1,
				V = [{
					type: c.STREAM_INFO,
					listener: m
				}, {
					type: c.STREAM_INFO_UPDATE,
					listener: _
				}, {
					type: c.CONFIG,
					listener: T
				}, {
					type: s.FRAME_DROP,
					listener: S
				}],
				Y = [{
					type: u.PLAY_STATS,
					listener: y
				}, {
					type: o.BUFFERING,
					listener: v
				}],
				B = [{
					type: o.PLAYBACK_STARTED,
					listener: g
				}];
			return f(), {
				destroy: h
			}
		}
		return {
			create: d
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e) {
			return e.reduce(function (e, t) {
				return e + t
			}, 0) / e.length
		}

		function t(e) {
			return Math.pow(e.reduce(function (e, t) {
				return e ? t ? e * t : e : t
			}, 0), 1 / e.length)
		}

		function n(e) {
			var t = e.reduce(function (e, t, n) {
				return t ? n - 1 ? e + 1 / t : (e ? 1 / e : 0) + 1 / t : e
			}, 0);
			return t ? e.length / t : 0
		}

		function i(i) {
			function r(e) {
				for (a.push(e); a.length > i;) a.shift();
				return o(), {
					minimum: s.min,
					maximum: s.max,
					arithmetic: s.arithmetic,
					geometric: s.geometric,
					harmonic: s.harmonic
				}
			}

			function o() {
				var i = JSON.parse(JSON.stringify(a)).sort(function (e, t) {
					return e - t
				}, 0);
				s.arithmetic = e(i), s.geometric = t(i), s.harmonic = n(i), s.minimum = i[0], s.maximum = i[i.length - 1]
			}
			var a = [],
				s = {};
			return s = {
				add: r,
				minimum: 0,
				maximum: 0,
				arithmetic: 0,
				geometric: 0,
				harmonic: 0,
				values: a
			}
		}
		return {
			create: i
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e) {
			var t = ["min", "start", "target", "limit", "max"],
				n = t.filter(function (t) {
					return !e.hasOwnProperty(t)
				});
			if (n.length) return {
				success: !1,
				reason: "The buffer config is invalid, it must contain " + n.join(", ") + ". Reset to default."
			};
			for (var i in t) {
				if ("number" != typeof e[t[i]]) return {
					success: !1,
					reason: "The buffer config is invalid, the value '" + t[i] + "' have to be a number. Reset to default."
				};
				if (i > 0 && e[t[i - 1]] > e[t[i]]) return {
					success: !1,
					reason: "The buffer config is invalid, the value '" + t[i - 1] + "' have to be lower then '" + t[i] + "'. Reset to default."
				}
			}
			return {
				success: !0
			}
		}

		function t(e) {
			var t = ["offsetThreshold", "offsetStep", "cooldownTime"],
				n = t.filter(function (t) {
					return !e.hasOwnProperty(t)
				});
			return n.length ? {
				success: !1,
				reason: "The dynamic buffer config is invalid, it must contain " + n.join(", ") + ". Reset to default."
			} : (t.forEach(function (t) {
				if ("number" != typeof e[t] || 0 === e[t]) return {
					success: !1,
					reason: "The dynamic buffer config is invalid, the value '" + t + "' have to be a number. Reset to default."
				}
			}), {
				success: !0
			})
		}
		return {
			validateBuffer: e,
			validateDynamic: t
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(137), n(29), n(138), n(139), n(126), n(123), n(127)], r = function (e, t, n, i, r, o, a, s, c) {
		function u(u) {
			function f() {
				i.add({
					target: u,
					listeners: K
				});
				var e = [o.TIME_UPDATE];
				for (var t in o) o.hasOwnProperty(t) && e.indexOf(o[t]) === -1 && j.push({
					type: o[t],
					listener: I
				})
			}

			function h() {
				i.remove({
					target: u,
					listeners: K
				}), m()
			}

			function E(e) {
				k = t.mustUseHLS || e.data.config.playback.allowSafariHlsFallback && t.canUseHLS, e.data.config.playback && (Y = !!e.data.config.playback.muted, B = !!e.data.config.playback.automute)
			}

			function p(e) {
				if (P = document.getElementById(e.data.elementId), V = !!P, G.debug("using " + (V ? "external " : "generated ") + "video eleement"), D = e.data.container, P ? (U = P.parentElement, L = P.style.cssText, P.style.cssText = "") : (P = document.createElement("video"), P.id = e.data.elementId, P.autoplay = !1, P.controls = !1, P.setAttribute("playsinline", !0), P.style.backgroundColor = "transparent"), P.muted = Y, P.volume = W, w = document.createElement("source"), P.appendChild(w), t.isIOS11) {
					G.debug("iOS11 detected, using iframe hack"), P.style.width = "100vw", P.style.height = "100vh";
					var n = document.createElement("iframe");
					n.frameBorder = 0, n.scrolling = "no", n.style.cssText = "width: 100%;height: 100%;position: absolute;display: block;top: 0;left: 0;", n.addEventListener("load", function (e) {
						e.target.contentDocument.body.style.height = "100%", e.target.contentDocument.body.style.width = "100%", e.target.contentDocument.body.style.overflow = "hidden", e.target.contentDocument.body.style.position = "absolute", e.target.contentDocument.body.style.margin = "0", e.target.contentDocument.body.style.padding = "0", e.target.contentDocument.body.firstChild || (e.target.contentDocument.body.appendChild(P), i.remove({
							target: P,
							listeners: j
						}), i.add({
							target: P,
							listeners: j
						}))
					}), n.addEventListener("unload", function (e) {
						e.target.contentDocument.body.firstChild && e.target.contentDocument.body.removeChild(P)
					}), D.appendChild(n)
				} else G.debug("appending video to container"), D.appendChild(P), i.add({
					target: P,
					listeners: j
				});
				t.isIEorEdge && (["scroll", "resize"].forEach(function (e) {
					window.addEventListener && addEventListener(e, C, !1), window.attachEvent && attachEvent("on" + e, C)
				}), C()), u.emit(c.ELEMENT_CREATED, {
					video: P
				}), u.emit(c.VOLUME_CHANGE, {
					muted: P.muted,
					volume: P.volume
				})
			}

			function m() {
				if (P) {
					for (i.remove({
							target: P,
							listeners: j
						}), t.isIEorEdge && (F = void 0, ["scroll", "resize"].forEach(function (e) {
							window.removeEventListener && removeEventListener(e, C, !1), window.detachEvent && detachEvent("on" + e, C)
						})), k && (w.src = "", P.load()); D.firstChild;) D.removeChild(D.firstChild);
					for (; P.firstChild;) P.removeChild(P.firstChild);
					V && (G.debug("releasing external video"), P.style.cssText = L, L = null, U.appendChild(P)), clearInterval(M), clearInterval(x), P = null
				}
			}

			function _(e) {
				P && (w.src = e.data.src, P.type = e.data.type, P.load(), clearInterval(M), clearInterval(x), e.data.src && e.data.src.length && (M = setInterval(R, l), "function" == typeof P.getVideoPlaybackQuality && (x = setInterval(O, d))))
			}

			function T() {
				G.debug("mute"), Y = !0, P && (P.muted = !0)
			}

			function y() {
				G.debug("unmute"), Y = !1, P && (P.muted = !1)
			}

			function v(e) {
				G.debug("onPlay"), P && n.play(P).then(function () {
					G.debug("play promise resolved"), u.emit(c.PLAY_START_SUCCESS), H = !1
				}).catch(function (t) {
					G.debug("play promise rejected"), t.error.name !== r.NOT_ALLOWED_ERROR || P.muted || !B || H ? P.muted && H && (G.debug("unmute to reset"), y(), P.muted || (I({
						type: o.VOLUME_CHANGE
					}), H = !1)) : (G.debug("mute to play"), T(), P.muted && (H = !0, setTimeout(v.bind(null, {
						data: {
							external: !1
						}
					}), 20))), e.data.external && !H || u.emit(c.PLAY_START_ERROR, {
						error: t.error,
						automuted: H
					})
				})
			}

			function g() {
				if (G.debug("onPause"), P && (P.pause(), A({
						data: {
							rate: 1
						}
					}), t.isIOS11))
					for (var e = 0; e < D.children.length; e += 1) {
						var n = D.children[e];
						n && n.tagName && "IFRAME" === n.tagName && n.contentWindow.location.reload(!0)
					}
			}

			function S(e) {
				W = Math.min(1, Math.max(0, e.data.volume)), P && P.volume !== W && (G.debug("set volume: " + e.data.volume), P.volume = W)
			}

			function A(e) {
				P && P.playbackRate !== e.data.rate && (G.debug("set rate to: " + e.data.rate), P.playbackRate = e.data.rate)
			}

			function N(e) {
				P && (G.debug("seek to: " + e.data.position), P.currentTime = e.data.position)
			}

			function I(e) {
				var t = b();
				switch (e.type) {
					case o.ERROR:
						t.code = P.error.code;
						break;
					case o.VOLUME_CHANGE:
						t.muted = P.muted, t.volume = P.volume;
						break;
					case o.CAN_PLAY:
						t.haveVideo = "undefined" != typeof P.videoTracks && P.videoTracks.length > 0, t.haveAudio = "undefined" != typeof P.audioTracks && P.audioTracks.length > 0, t.videoWidth = P.videoWidth, t.videoHeight = P.videoHeight
				}
				G.detail(e.type), u.emit("mediaElementProxy." + e.type, t)
			}

			function R() {
				u.emit(c.TIME_UPDATE, b())
			}

			function O() {
				var e = b();
				e.quality = P.getVideoPlaybackQuality(), u.emit(c.QUALITY_UPDATE, e)
			}

			function C() {
				var e = P && "function" == typeof P.getBoundingClientRect ? P.getBoundingClientRect() : {
						top: 0,
						left: 0,
						bottom: 0,
						right: 0
					},
					t = window.innerHeight || document.documentElement.clientHeight,
					n = window.innerWidth || document.documentElement.clientWidth,
					i = e.top <= t && e.top + e.height >= 0,
					r = e.left <= n && e.left + e.width >= 0,
					o = i && r;
				o !== F && (G.debug("viewport changed to " + (o ? "visible" : "hidden")), F = o, u.emit(o ? c.VIEWPORT_VISIBLE : c.VIEWPORT_HIDDEN))
			}

			function b() {
				return {
					currentTime: P.currentTime,
					buffered: P.buffered,
					played: P.played,
					playbackRate: P.playbackRate
				}
			}
			var P, D, w, L, U, M, x, F, k, G = e.create("MediaElementProxy"),
				V = !1,
				Y = !1,
				B = !1,
				H = !1,
				W = 1,
				K = [{
					type: s.CONFIG,
					listener: E
				}, {
					type: a.CREATE_VIDEO,
					listener: p
				}, {
					type: a.DESTROY_VIDEO,
					listener: m
				}, {
					type: a.VIDEO_SOURCE,
					listener: _
				}, {
					type: a.PLAY,
					listener: v
				}, {
					type: a.PAUSE,
					listener: g
				}, {
					type: a.SEEK,
					listener: N
				}, {
					type: a.MUTE,
					listener: T
				}, {
					type: a.UNMUTE,
					listener: y
				}, {
					type: a.SET_VOLUME,
					listener: S
				}, {
					type: a.SET_RATE,
					listener: A
				}],
				j = [];
			return f(), {
				destroy: h
			}
		}
		var l = 100,
			d = 1e3;
		return {
			create: u
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e(e) {
			return new Promise(function (i, r) {
				var o = e.play();
				Promise.all([o]).then(function (t) {
					i({
						media: e
					})
				}).catch(function (i) {
					t = i && i.name ? i.name : "Unknown", n = i && i.message ? i.message : "Message not available.", r({
						media: e,
						error: {
							name: t,
							message: n
						}
					})
				})
			})
		}
		var t, n;
		return {
			play: e
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return {
			ABORT_ERROR: "AbortError",
			NOT_ALLOWED_ERROR: "NotAllowedError"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "";
		return {
			BASE: e,
			LOAD_START: e + "loadstart",
			PROGRESS: e + "progress",
			SUSPEND: e + "suspend",
			ABORT: e + "abort",
			EMPTIED: e + "emptied",
			STALLED: e + "stalled",
			PLAY: e + "play",
			PAUSE: e + "pause",
			LOADED_META_DATA: e + "loadedmetadata",
			LOADED_DATA: e + "loadeddata",
			WAITING: e + "waiting",
			ERROR: e + "error",
			PLAYING: e + "playing",
			CAN_PLAY: e + "canplay",
			CAN_PLAY_THROUGH: e + "canplaythrough",
			SEEKING: e + "seeking",
			SEEKED: e + "seeked",
			TIME_UPDATE: e + "timeupdate",
			ENDED: e + "ended",
			RATE_CHANGE: e + "ratechange",
			DURATION_CHANGE: e + "durationchange",
			VOLUME_CHANGE: e + "volumechange"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(29), n(105), n(141), n(126), n(127), n(128), n(142)], r = function (e, t, n, i, r, o, a, s) {
		function c(c) {
			function u() {
				v.debug("create"), t.add({
					target: c,
					listeners: g
				}), l()
			}

			function l() {
				y = new window.MediaSource, t.add({
					target: y,
					listeners: S
				}), A.push(s.create(c, y))
			}

			function d() {
				if (y) {
					for (; A.length;) A.pop().destroy();
					t.remove({
						target: y,
						listeners: S
					}), y = null
				}
			}

			function f() {
				v.debug("destroy"), d(), t.remove({
					target: c,
					listeners: g
				})
			}

			function h() {
				c.emit(r.VIDEO_SOURCE, {
					src: window.URL.createObjectURL(y),
					type: n.MP4
				})
			}

			function E() {
				d(), l(), h()
			}

			function p() {
				c.emit(a.SOURCE_OPEN, T())
			}

			function m() {
				E(), c.emit(a.SOURCE_ENDED, T())
			}

			function _() {
				E(), c.emit(a.SOURCE_CLOSED, T())
			}

			function T() {
				return {
					readyState: y.readyState
				}
			}
			var y, v = e.create("MediaSourceProxy"),
				g = [{
					type: o.ELEMENT_CREATED,
					listener: h
				}, {
					type: r.UPDATE_SOURCE,
					listener: E
				}],
				S = [{
					type: i.SOURCE_OPEN,
					listener: p
				}, {
					type: i.SOURCE_ENDED,
					listener: m
				}, {
					type: i.SOURCE_CLOSED,
					listener: _
				}],
				A = [];
			return u(), {
				destroy: f
			}
		}
		return {
			create: c
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "";
		return {
			BASE: e,
			SOURCE_OPEN: e + "sourceopen",
			SOURCE_ENDED: e + "sourceended",
			SOURCE_CLOSED: e + "sourceclosed"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(29), n(143), n(141), n(126), n(127), n(123), n(128), n(28)], r = function (e, t, n, i, r, o, a, s, c, u) {
		function l(o, l) {
			function d() {
				n.add({
					target: o,
					listeners: j
				})
			}

			function f() {
				for (; Y.length;) Y.pop();
				n.remove({
					target: o,
					listeners: j
				}), n.remove({
					target: o,
					listeners: z
				}), b && n.remove({
					target: b,
					listeners: q
				}), b = null
			}

			function h(e) {
				P = e.data.currentTime
			}

			function E(e) {
				p(e.data)
			}

			function p(e) {
				_({
					command: B,
					fragment: e
				})
			}

			function m(e, t) {
				_({
					command: H,
					from: e,
					to: t
				})
			}

			function _(e) {
				e.count = k++, V ? T(e) : y(e)
			}

			function T(e) {
				if (w.debug("queue " + e.command + " " + e.count), e.force) Y.unshift(e);
				else if (e.command === B && Y.length && Y[Y.length - 1].command === B) {
					if (G + 1 < x) {
						var t = Y[Y.length - 1].fragment,
							n = e.fragment,
							i = new Uint8Array(t.byteLength + n.byteLength);
						i.set(new Uint8Array(t), 0), i.set(new Uint8Array(n), t.byteLength), Y[Y.length - 1].fragment = i, Y[Y.length - 1].count = Y[Y.length - 1].count.toString() + " & " + e.count, G++
					} else G = 0, w.debug("joined fragments threshold (" + x + ") reached so begin new one"), Y.push(e);
					w.debug("joined fragments count " + G)
				} else G = 0, w.debug("joined fragments count " + G), Y.push(e)
			}

			function y(e) {
				switch (w.debug("execute " + e.command + " " + e.count), e.command) {
					case B:
						W && (V = !0, b.appendBuffer(e.fragment), C() || F++);
						break;
					case H:
						b.buffered.length && b.buffered.end(b.length - 1) > 0 && (V = !0, b.remove(e.from, e.to))
				}
			}

			function v() {
				V = !1, K && (K = 0, o.emit(c.SOURCE_READY)), Y.length ? y(Y.shift()) : O() && C() && m(b.buffered.start(0), P - U)
			}

			function g(e) {
				D = e.data.onStreamInfo;
				var n = D && D.mimeType ? D.mimeType : 'video/mp4; codecs="avc1.42E01E, mp4a.40.2"';
				l && "open" === l.readyState ? b ? (W = !0, t.isIEorEdge && !isNaN(l.duration) && l.duration > 0 ? (V = !0, K = 1, l.duration = 0) : o.emit(c.SOURCE_READY)) : N(n) : l.addEventListener(r.SOURCE_OPEN, A.bind(null, n))
			}

			function S(e) {}

			function A(e) {
				l.removeEventListener(r.SOURCE_OPEN, A), N(e)
			}

			function N(e) {
				!b && l && "open" === l.readyState && (b = l.addSourceBuffer(e), n.add({
					target: o,
					listeners: z
				}), n.add({
					target: b,
					listeners: q
				}), W = !0, o.emit(c.SOURCE_READY))
			}

			function I(e) {
				e.data.state === u.PAUSED && R()
			}

			function R() {
				for (W = !1; Y.length;) Y.pop();
				m(0, 1 / 0), F = 0
			}

			function O() {
				return b.buffered.length && b.buffered.start(0) < P - L
			}

			function C() {
				return M < F
			}
			var b, P, D, w = e.create("SourceBufferQueue"),
				L = 30,
				U = 20,
				M = 300,
				x = 30,
				F = 0,
				k = 0,
				G = 0,
				V = !1,
				Y = [],
				B = "append",
				H = "remove",
				W = !0,
				K = 0,
				j = [{
					type: s.STREAM_INFO,
					listener: g
				}, {
					type: s.STREAM_INFO_UPDATE,
					listener: S
				}],
				z = [{
					type: a.TIME_UPDATE,
					listener: h
				}, {
					type: s.APPLICATION_STATE_CHANGE,
					listener: I
				}, {
					type: s.STREAM_FRAGMENT,
					listener: E
				}],
				q = [{
					type: i.UPDATE_END,
					listener: v
				}];
			return d(), {
				destroy: f
			}
		}
		return {
			create: l
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		var e = "";
		return {
			BASE: e,
			ABORT: e + "abort",
			ERROR: e + "error",
			UPDATE: e + "update",
			UPDATE_START: e + "updatestart",
			UPDATE_END: e + "updateend"
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(29), n(126), n(123)], r = function (e, t, n, i) {
		function r(r) {
			function o() {
				l.debug("initialize"), t.add({
					target: r,
					listeners: d
				})
			}

			function a() {
				c(), t.remove({
					target: r,
					listeners: d
				})
			}

			function s(e) {
				c(), e.data.onStreamInfo.haveAudio || (window.AudioContext = window.AudioContext || window.webkitAudioContext || function () {
					return {
						close: function () {}
					}
				}, u = new AudioContext)
			}

			function c() {
				u && "function" == typeof u.close && (u.close(), u = null)
			}
			var u, l = e.create("FakeAudioContext"),
				d = [{
					type: i.STREAM_INFO,
					listener: s
				}, {
					type: n.PAUSE,
					listener: c
				}];
			return o(), {
				destroy: a
			}
		}
		return {
			create: r
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(12), n(29), n(126), n(123), n(127), n(28)], r = function (e, t, n, i, r, o, a) {
		function s(s) {
			function c() {
				g.debug("initialize"), n.add({
					target: s,
					listeners: A
				})
			}

			function u() {
				n.remove({
					target: s,
					listeners: A
				}), n.remove({
					target: s,
					listeners: N
				})
			}

			function l(e) {
				m = e.data.state, n.remove({
					target: s,
					listeners: N
				}), (t.isIEorEdge && m === a.PLAYING || !t.isIEorEdge && m !== a.PAUSED) && n.add({
					target: s,
					listeners: N
				})
			}

			function d(e) {
				var t = h(e.data.currentTime, e.data.buffered),
					n = E(e.data.currentTime, e.data.buffered);
				if (_ === e.data.currentTime) {
					if (S++, S < T) return;
					if (n > t && p(e.data.buffered, n) > v && f(e.data.currentTime, e.data.buffered)) {
						var r = e.data.buffered.start(E(e.data.currentTime, e.data.buffered));
						g.debug("recover gap, seek to " + r, 3), s.emit(i.SEEK, {
							position: r
						}), s.emit(i.PLAY, {
							external: !1
						})
					}
				}
				_ = e.data.currentTime, S = 0
			}

			function f(e, t) {
				return e + y >= t.end(h(e, t))
			}

			function h(e, t) {
				for (var n = 0; n < t.length - 1 && t.start(n + 1) <= e;) n++;
				return n
			}

			function E(e, t) {
				for (var n = 0; n < t.length - 1 && t.start(n) <= e;) n++;
				return n
			}

			function p(e, t) {
				return t < e.length ? e.end(t) - e.start(t) : 0
			}
			var m, _, T = 10,
				y = .2,
				v = .5,
				g = e.create("GapHandler"),
				S = 0,
				A = [{
					type: r.APPLICATION_STATE_CHANGE,
					listener: l
				}],
				N = [{
					type: o.TIME_UPDATE,
					listener: d
				}];
			return c(), {
				destroy: u
			}
		}
		return {
			create: s
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(80), n(29), n(125), n(123), n(130), n(28)], r = function (e, t, n, i, r, o, a) {
		function s(s) {
			function c() {
				E.debug("initialize"), n.add({
					target: s,
					listeners: p
				})
			}

			function u() {
				n.remove({
					target: s,
					listeners: p
				})
			}

			function l(e) {
				n.remove({
					target: s,
					listeners: m
				});
				var t = e.data.state;
				switch (t) {
					case a.PAUSED:
					case a.PAUSING:
						E.debug("entering paused state"), f = null;
						break;
					case a.PLAYING:
						n.add({
							target: s,
							listeners: m
						})
				}
			}

			function d(e) {
				if (f && "undefined" != typeof f.currentTime && e.data.stats.currentTime < f.currentTime - h) {
					var n = {
						currentTime: {
							last: f.currentTime,
							current: e.data.stats.currentTime
						}
					};
					s.emit(i.ERROR, n)
				}
				f = t.copy(e.data.stats)
			}
			var f, h = 2,
				E = e.create("HLSJumpHandler"),
				p = [{
					type: r.APPLICATION_STATE_CHANGE,
					listener: l
				}],
				m = [{
					type: o.PLAY_STATS,
					listener: d
				}];
			return c(), {
				destroy: u
			}
		}
		return {
			create: s
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(9), n(13), n(12), n(29), n(125), n(126), n(123), n(127), n(28)], r = function (e, t, n, i, r, o, a, s, c) {
		function u(u) {
			function l() {
				T.debug("initialize"), i.add({
					target: u,
					listeners: g
				})
			}

			function d() {
				i.remove({
					target: u,
					listeners: g
				})
			}

			function f(t) {
				v = t.name === a.DOCUMENT_HIDDEN, T.debug(v ? "hidden" : "visible"), v && e.mobile && (m === c.PLAYING || m === c.LOADING || m === c.BUFFERING) && u.emit(r.SUSPENDED)
			}

			function h(t) {
				var n = t.data.buffered.length ? t.data.buffered.end(0) - t.data.currentTime : "n.a.";
				(_ && t.name === s.PAUSE || v && e.mobile) && m === c.PLAYING ? u.emit(r.SUSPENDED) : y || (y = setTimeout(function () {
					m === c.PLAYING && (T.debug("nanoplayer: recover unexpected pause type " + t.name + ", delay = " + n, 3), u.emit(o.PLAY, {
						external: !1
					})), y = 0
				}, 200))
			}

			function E(e) {
				m = e.data.state
			}

			function p(e) {
				_ = n.mustUseHLS || e.data.config.playback.allowSafariHlsFallback && n.canUseHLS
			}
			var m, _, T = t.create("PauseHandler"),
				y = 0,
				v = !1,
				g = [{
					type: s.PAUSE,
					listener: h
				}, {
					type: s.WAITING,
					listener: h
				}, {
					type: s.ENDED,
					listener: h
				}, {
					type: a.APPLICATION_STATE_CHANGE,
					listener: E
				}, {
					type: a.CONFIG,
					listener: p
				}, {
					type: a.DOCUMENT_VISIBLE,
					listener: f
				}, {
					type: a.DOCUMENT_HIDDEN,
					listener: f
				}];
			return l(), {
				destroy: d
			}
		}
		return {
			create: u
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(13), n(29), n(28), n(126), n(123), n(127), n(129)], r = function (e, t, n, i, r, o, a) {
		function s(i) {
			function s() {
				t.add({
					target: i,
					listeners: C
				})
			}

			function c() {
				t.remove({
					target: i,
					listeners: C
				}), t.remove({
					target: i,
					listeners: b
				})
			}

			function u() {
				for (T = {
						droppedVideoFrames: 0,
						droppedVideoFramesCurrent: 0,
						corruptedVideoFrames: 0,
						corruptedVideoFramesCurrent: 0,
						creationTime: 0,
						totalVideoFrames: 0
					}; N.length;) N.pop()
			}

			function l(e) {
				y = e.data.state, y === n.PAUSED ? E() : !O && I && y === n.PLAYING && h()
			}

			function d(e) {
				I = e.data.onStreamInfo.haveVideo && (R = e.data.onStreamInfo.videoInfo.frameRate)
			}

			function f(e) {
				d(e)
			}

			function h() {
				E(), t.add({
					target: i,
					listeners: b
				}), O = setInterval(m, g)
			}

			function E() {
				clearInterval(O), t.remove({
					target: i,
					listeners: b
				}), O = 0, u()
			}

			function p(e) {
				for (var t in e.data.quality)
					if (T.hasOwnProperty(t)) {
						if ("droppedVideoFrames" === t) {
							for (T.droppedVideoFramesCurrent = e.data.quality.droppedVideoFrames - T.droppedVideoFrames; N.length > A - 1;) N.shift();
							N.push({
								count: T.droppedVideoFramesCurrent,
								time: e.data.currentTime
							})
						}
						"corruptedVideoFrames" === t && (T.corruptedVideoFramesCurrent = e.data.quality.corruptedVideoFrames - T.corruptedVideoFrames), T[t] = e.data.quality[t]
					} i.emit(a.QUALITY_STATS, T)
			}

			function m() {
				if (y === n.PLAYING) {
					var e = _("extreme");
					if (!e && (e = _("high")), !e && (e = _("medium")), e) {
						for (v.debug("frame drop detected with type '" + e.type + "'"), v.debug(e.frames.dropped + " frames in last " + e.samples.all.length + " seconds dropped"); N.length;) N.pop();
						i.emit(a.FRAME_DROP, e)
					}
				}
			}

			function _(e) {
				if (N.length > S[e].samplesRange - 1) {
					var t = N.slice(-S[e].samplesRange),
						n = t.filter(function (t) {
							return t.count >= R * S[e].ratioThreshold
						});
					if (n.length > S[e].samplesMatchAllowed) {
						var i = 0;
						return t.forEach(function (e) {
							i += e.count
						}), {
							type: e,
							framerate: R,
							samples: {
								all: t,
								matched: n,
								limit: Math.ceil(R * S[e].ratioThreshold)
							},
							frames: {
								dropped: i
							}
						}
					}
				}
				return !1
			}
			var T, y, v = e.create("QualityHandler"),
				g = 1e3,
				S = {
					extreme: {
						ratioThreshold: .95,
						samplesRange: 3,
						samplesMatchAllowed: 1
					},
					high: {
						ratioThreshold: .2,
						samplesRange: 5,
						samplesMatchAllowed: 1
					},
					medium: {
						ratioThreshold: .1,
						samplesRange: 10,
						samplesMatchAllowed: 3
					}
				},
				A = 10,
				N = [],
				I = !1,
				R = 0,
				O = 0,
				C = [{
					type: r.APPLICATION_STATE_CHANGE,
					listener: l
				}, {
					type: r.STREAM_INFO,
					listener: d
				}, {
					type: r.STREAM_INFO_UPDATE,
					listener: f
				}],
				b = [{
					type: o.QUALITY_UPDATE,
					listener: p
				}];
			return s(), {
				destroy: c
			}
		}
		return {
			create: s
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(92), n(29), n(12), n(134), n(28), n(123), n(126), n(127), n(130)], r = function (e, t, n, i, r, o, a, s, c) {
		function u(a) {
			function u() {
				y(), t.add({
					target: a,
					listeners: D
				}), t.add({
					target: a,
					listeners: w
				})
			}

			function l() {
				t.remove({
					target: a,
					listeners: w
				}), t.remove({
					target: a,
					listeners: D
				})
			}

			function d(e) {
				switch (g = e.data.state) {
					case r.PAUSED:
						t.remove({
							target: a,
							listeners: L
						});
						break;
					case r.PLAYING:
						break;
					case r.LOADING:
						y(), S && t.add({
							target: a,
							listeners: L
						})
				}
			}

			function f(e) {
				S = n.mustUseHLS || e.data.config.playback.allowSafariHlsFallback && n.canUseHLS
			}

			function h(e) {
				C += 1, b += e.data.byteLength
			}

			function E(e) {
				!S && t.add({
					target: a,
					listeners: L
				}), A = e.data.onStreamInfo.haveVideo
			}

			function p(e) {
				A = e.data.onStreamInfo.haveVideo
			}

			function m(t) {
				var n = t.data.buffered.length - 1,
					i = t.data.played.length - 1;
				if (v.currentTime = g === r.LOADING ? 0 : t.data.currentTime, i >= 0 && (v.playout.start = t.data.played.start(i),
						v.playout.end = t.data.played.end(i)), n >= 0 && (!S || S && (v.buffer.end !== t.data.buffered.end(n) || t.data.buffered.end(n) < v.currentTime)) && (v.buffer.update.current = t.data.buffered.end(n) - v.buffer.end, v.buffer.delay.before = v.buffer.end - v.currentTime, v.buffer.start = t.data.buffered.start(n), v.buffer.end = t.data.buffered.end(n), v.buffer.delay.current = v.buffer.end - v.currentTime, N.add(v.buffer.delay.current), T(N, v.buffer.delay), I.add(v.buffer.update.current), T(I, v.buffer.update)), e && !S) {
					var o = Math.floor(e.now() / 1e3);
					P !== o && (P = o, v.bitrate.current = 8 * b, R.add(v.bitrate.current), T(R, v.bitrate), A && (v.framerate.current = C, O.add(v.framerate.current), T(O, v.framerate)), C = 0, b = 0)
				}
				_(t)
			}

			function _(e) {
				a.emit(c.PLAY_STATS, {
					stats: v,
					buffered: e ? e.data.buffered : null,
					played: e ? e.data.played : null,
					currentTime: e ? e.data.currentTime : 0,
					playbackRate: e ? e.data.playbackRate : 0
				})
			}

			function T(e, t) {
				t.avg = e.arithmetic, t.min = e.minimum, t.max = e.maximum
			}

			function y() {
				v = {
					currentTime: 0,
					playout: {
						start: 0,
						end: 0
					},
					buffer: {
						start: 0,
						end: 0,
						delay: {
							before: 0,
							current: 0,
							avg: 0,
							min: 0,
							max: 0
						},
						update: {
							current: 0,
							avg: 0,
							min: 0,
							max: 0
						}
					},
					quality: {
						droppedVideoFrames: 0,
						droppedVideoFramesCurrent: 0,
						corruptedVideoFrames: 0,
						corruptedVideoFramesCurrent: 0,
						creationTime: 0,
						totalVideoFrames: 0
					},
					bitrate: {
						current: 0,
						avg: 0,
						min: 0,
						max: 0
					},
					framerate: {
						current: 0,
						avg: 0,
						min: 0,
						max: 0
					}
				}, N = i.create(10), I = i.create(10), R = i.create(10), O = i.create(10), C = 0, b = 0, _(null)
			}
			var v, g, S, A, N, I, R, O, C, b, P = 0,
				D = [{
					type: o.APPLICATION_STATE_CHANGE,
					listener: d
				}, {
					type: o.CONFIG,
					listener: f
				}],
				w = [{
					type: o.STREAM_FRAGMENT,
					listener: h
				}, {
					type: o.STREAM_INFO,
					listener: E
				}, {
					type: o.STREAM_INFO_UPDATE,
					listener: p
				}],
				L = [{
					type: s.TIME_UPDATE,
					listener: m
				}];
			return u(), {
				destroy: l
			}
		}
		return {
			create: u
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(123)], r = function (e) {
		function t(t) {
			function n() {
				o() && t.addListener(e.STREAM_FRAGMENT, r)
			}

			function i() {
				t.removeListener(e.STREAM_FRAGMENT, r)
			}

			function r(e) {
				window.fragments || (window.fragments = []), window.fragments.push(e.data)
			}

			function o() {
				return !!a("nanoDump")
			}

			function a(e) {
				return document.cookie.indexOf(e + "=") !== -1 ? document.cookie.split(e + "=")[1].split(";")[0] : null
			}
			return n(), {
				destroy: i
			}
		}
		return {
			create: t
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(73), n(82), n(79), n(10), n(2), n(9)], r = function (e, t, n, i, r, o) {
		"use strict";

		function a(e) {
			this.version = n.CORE, this.type = "native", this.config = {
				playback: {
					autoplay: !0,
					muted: !1
				},
				source: {
					hls: void 0
				}
			}, this._playerDivId = e
		}
		a.isSupported = function () {
			return "iOS" === o.os || "Safari" === o.browser
		}, a.supportedTechniques = [t.NATIVE];
		var s = a.prototype = Object.create(e.prototype);
		return s.setup = function (e) {
			var t = this;
			return new Promise(function (n, i) {
				try {
					r.merge(e, t.config), r.clean(t.config), window.location = t.config.source.hls, n(t.config)
				} catch (e) {
					i(e)
				}
			})
		}, s.play = function () {}, s.pause = function () {}, s.mute = function () {}, s.unmute = function () {}, s.setVolume = function () {}, a
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [n(73), n(82), n(4), n(153), n(5), n(79), n(154), n(11), n(10), n(2), n(3), n(16), n(74), n(14), n(155), n(28), n(72), n(109)], r = function (e, t, n, i, r, o, a, s, c, u, l, d, f, h, E, p, m, _) {
		function T(e) {
			this.version = o.CORE, this.type = "flash", this.config = d.create(), this._volume = 1, this._muted = !1, this._mediaElement = null, this._mediaSource = null, this._playing = !1, this._playerDivId = e, this._playerDiv = document.getElementById(this._playerDivId), this._mediaElementId = "flash-" + e, this.state = this.STATE.IDLE, this._triggered = 0, this._stats = {
				currentTime: 0,
				playout: {
					start: 0,
					end: 0
				},
				buffer: {
					start: 0,
					end: 0,
					delay: {
						before: 0,
						current: 0,
						avg: 0,
						min: 0,
						max: 0
					},
					update: {
						current: 0,
						avg: 0,
						min: 0,
						max: 0
					}
				},
				quality: {
					droppedVideoFrames: 0,
					droppedVideoFramesCurrent: 0,
					corruptedVideoFrames: 0,
					corruptedVideoFramesCurrent: 0,
					creationTime: 0,
					totalVideoFrames: 0
				},
				bitrate: {
					current: 0,
					avg: 0,
					min: 0,
					max: 0
				},
				framerate: {
					current: 0,
					avg: 0,
					min: 0,
					max: 0
				}
			}, this._intervalEmitStats = 0, this._streamInfo = null
		}
		var y = c.log,
			v = 2e4,
			g = 1e4,
			S = 6e4,
			A = 2e4,
			N = 1e4,
			I = 6e4;
		T.isSupported = function () {
			return 0 !== a.ua.pv[0] || a.ua.pv === [0, 0, 0]
		}, T.supportedTechniques = [t.FLASH];
		var R = T.prototype = Object.create(e.prototype);
		return R.setup = function (e) {
			return new Promise(function (t, i) {
				try {
					var o = u.check(e, f.create());
					u.merge(e, this.config), u.clean(this.config);
					var a = this.config.source.h5live.rtmp,
						c = this.config.source.h5live.params;
					if (!(a && a.url && a.streamname || c && c.url && c.stream)) throw new l(n.CONFIG_RTMP, r.SETUP.CONFIG_RTMP);
					this._emitter = new s, this._setListeners(this.config.events, this), o.forEach(function (e) {
						this._emitWarning(e)
					}.bind(this)), this._metaDataEnabled = this.config.playback.metadata, this._setupVideoElement(), this._setupFinish().then(t, i)
				} catch (e) {
					i(e)
				}
			}.bind(this))
		}, R.play = function () {
			this._setMediaElement(), this._mediaElement.Start()
		}, R.pause = function () {
			this._triggered = arguments.length ? arguments[0] : 0, clearTimeout(this._bufferTimeout), this._setMediaElement(), this._mediaElement.Stop()
		}, R.mute = function () {
			this._setMediaElement(), this._muted = !0, this._mediaElement.SetVolume(0), this._emit(h.MUTE)
		}, R.unmute = function () {
			this._setMediaElement(), this._muted = !1, this._mediaElement.SetVolume(this._volume), this._emit(h.UNMUTE)
		}, R.setVolume = function (e) {
			e < 0 && (e = 0), e > 1 && (e = 1), this._setMediaElement(), this._volume = e, this._muted || this._mediaElement.SetVolume(this._volume), this._emit(h.VOLUME_CHANGE, {
				volume: e
			})
		}, R.destroy = function () {
			this._setState(this.STATE.DESTROYING), this._playing && this.pause(this.state), this._emit(h.DESTROY), this._mediaElement && (this.pause(), document.getElementById(this._playerDivId).removeChild(this._mediaElement), this._mediaElement = null), clearTimeout(this._bufferTimeout), this.removeAllListeners()
		}, R.updateSource = function (e) {
			return new Promise(function (t, n) {
				try {
					var i = !1;
					this.state !== this.STATE.PLAYING && this.state !== this.STATE.LOADING && this.state !== this.STATE.BUFFERING || (i = !0, this._setState(p.PLAYBACK_RESTARTING), this.pause(this.state)), this.config.source = e, this._setConnectionConfig(this.config.source.h5live), i && this.play(), t(this.config)
				} catch (e) {
					n(e)
				}
			}.bind(this))
		}, R._setupVideoElement = function () {
			var e = "nano.player.swf";
			"undefined" != typeof this.config.playback.flashplayer && (e = this.config.playback.flashplayer);
			var t = document.createElement("div");
			t.setAttribute("id", "flashReplace"), this._playerDiv.appendChild(t);
			var n = "9.0.0",
				i = {},
				r = {};
			r.quality = "high", r.bgcolor = "#000000", r.allowscriptaccess = "always", r.allowfullscreen = "true", r.allowfullscreeninteractive = "true", r.wmode = "transparent";
			var o = {};
			o.id = this._mediaElementId, o.name = this._mediaElementId, o.align = "middle", o.wmode = "transparent", a.embedSWF(e, "flashReplace", "100%", "100%", n, null, i, r, o)
		}, R._setupFinish = function () {
			return new Promise(function (e, t) {
				function n() {
					if (a++, o && (this._mediaElement = document.getElementById(this._mediaElementId), this._mediaElement && this._mediaElement.Start)) {
						clearInterval(o);
						try {
							if (window._flashPlayers[this._mediaElementId] = this, this._setName(), this._mediaElement.TraceExternalFunctions(), this._setNetStream(!1), this._setConnectionConfig(this.config.source.h5live), this._setPlayerConfig(this._playerDiv.offsetWidth, this._playerDiv.offsetHeight), this.config.playback.muted && this.mute(), this.config.playback.timeouts) {
								var n = 1e3 * this.config.playback.timeouts.loading,
									s = 1e3 * this.config.playback.timeouts.buffering;
								v = Math.max(Math.min(n, S), g), A = Math.max(Math.min(s, I), N)
							}
							this.config.type = this.type, this._setState(this.STATE.READY), this._emit(h.READY, {
								config: this.config
							}), this.config.playback.autoplay && this.play(), e(this.config)
						} catch (e) {
							t(e)
						}
					}
					a > 100 && (clearInterval(o), t(new l(i.EMBED_PLAYER, r.SETUP.EMBED_PLAYER)))
				}
				var o = 0,
					a = 0;
				o = setInterval(n.bind(this), 100)
			}.bind(this))
		}, R._setListeners = function (e, t) {
			for (var n in e)
				if (e.hasOwnProperty(n) && "function" == typeof e[n]) {
					var i = n.replace("on", ""),
						r = e[n];
					t.on(i, r)
				}
		}, R._setState = function (e) {
			this.state = e, y("nanoplayer (" + this._playerDivId + '): set state "' + this._getState() + '"', 3), this._emit(h.STATE_CHANGE)
		}, R._getState = function () {
			for (var e in this.STATE)
				if (this.STATE[e] === this.state) return e
		}, R._emit = function (e, t) {
			var n = {};
			t && t.name && t.data ? n = t : t ? (n.data = t, n.name = e || "unknown") : (n.data = {}, n.name = e || "unknown"), n.player = this._playerDivId, n.id = this.id, n.version = this.version, n.state = this.state, "Error" === e && n.data.code && n.data.message && y("nanoplayer (" + this._playerDivId + "): error " + n.data.code + " " + n.data.message, 1), this.emit(n.name, n)
		}, R._emitError = function (e, t) {
			this._emit(h.ERROR, {
				code: e,
				message: t
			})
		}, R._emitWarning = function (e) {
			this._emit(h.WARNING, {
				message: e
			})
		}, R._setConnectionConfig = function (e) {
			this._setMediaElement();
			var t = e.rtmp,
				n = e.params,
				i = n && n.url ? n.url : t && t.url ? t.url : null,
				r = n && n.stream ? n.stream : t && t.streamname ? t.streamname : null;
			i && r ? (this.config.url = i + "/" + r, this._mediaElement.SetServerUrl(i), this._mediaElement.SetStreamName(r)) : this.config.url = ""
		}, R._setPlayerConfig = function (e, t) {
			this._setMediaElement(), this._mediaElement.SetVideoDisplayResolution(e + "," + t), this._mediaElement.SetResize("false"), this._mediaElement.SetOnlyThisResolution(e + "," + t)
		}, R._setNetStream = function (e) {
			this._setMediaElement(), this._mediaElement.UseNetstream(e ? 1 : 0)
		}, R._setMediaElement = function () {
			var e = document.getElementById(this._mediaElementId);
			this._mediaElement = e
		}, R._getPauseReason = function (e) {
			var t = "";
			if (e) switch (this.state) {
				case this.STATE.READY:
					t = m.SERVER_NOT_FOUND;
					break;
				case this.STATE.LOADING:
					t = m.STREAM_NOT_FOUND;
					break;
				case this.STATE.BUFFERING:
					t = m.BUFFER;
					break;
				case this.STATE.UNKNOWN:
					t = m.UNKNOWN;
					break;
				case this.STATE.PLAYING:
					t = m.NORMAL;
					break;
				case this.STATE.DESTROYING:
					t = m.DESTROY;
					break;
				case this.STATE.PLAYBACK_RESTARTING:
					t = m.PLAYBACK_RESTART;
					break;
				default:
					t = m.NORMAL
			} else t = m.NORMAL;
			return t
		}, R._startIntervals = function () {
			this._stopIntervals(), this._onStats(!0), this._intervalEmitStats = setInterval(this._onStats.bind(this), 100)
		}, R._stopIntervals = function () {
			this._intervalEmitStats && (clearInterval(this._intervalEmitStats), this._intervalEmitStats = 0)
		}, R._onStreamInfo = function (e) {
			y("nanoplayer (" + this._playerDivId + "): onStreamInfo", 2), this._streamInfo = e.onStreamInfo, this._emit(h.STREAM_INFO, {
				streamInfo: this._parseStreamInfo()
			})
		}, R._parseStreamInfo = function () {
			var e = {},
				t = [_.PREROLL_DURATION, _.MIME_TYPE];
			for (var n in _) {
				var i = _[n];
				_.hasOwnProperty(n) && this._streamInfo.hasOwnProperty(i) && t.indexOf(i) === -1 && (e[i] = this._streamInfo[i])
			}
			return e
		}, R._onStats = function (e) {
			var t = e ? 0 : this._stats.currentTime + .1;
			this._stats.currentTime = t;
			var n = "stats error";
			try {
				n = JSON.stringify(this._stats)
			} catch (e) {}
			y("nanoplayer (" + this._playerDivId + "): stats - " + n, 4), this._emit(h.STATS, {
				stats: this._stats
			})
		}, R._onLoading = function () {
			this._playing = !0, this._setState(this.STATE.LOADING), this._emit(h.LOADING), this._loadingTimeout = setTimeout(function () {
				this._emitError(r.STREAM.NOT_FOUND, "The requested stream can not be found."), this.pause(this.state)
			}.bind(this), v)
		}, R._onPlaying = function () {
			clearTimeout(this._loadingTimeout), clearTimeout(this._bufferTimeout), this._setState(this.STATE.PLAYING), this._startPlayingTimeout && clearTimeout(this._startPlayingTimeout), this._startPlayingTimeout = setTimeout(function () {
				clearTimeout(this._startPlayingTimeout), this._startPlayingTimeout = 0, this.state === this.STATE.PLAYING && (this._playing = !0, this.setVolume(this._volume), !this._intervalEmitStats && this._emit(h.PLAY, {
					stats: {
						connecting: 0,
						connected: 0,
						firstFragmentReceived: 0,
						firstFrameRendered: 0,
						playable: 0,
						playing: 0
					}
				}), !this._intervalEmitStats && this._startIntervals())
			}.bind(this), 1e3)
		}, R._onStartBuffering = function () {
			this.state !== this.STATE.LOADING && (this._setState(this.STATE.BUFFERING), this._startBufferTimeout && clearTimeout(this._startBufferTimeout), this._startBufferTimeout = setTimeout(function () {
				clearTimeout(this._startBufferTimeout), this._startBufferTimeout = 0, this.state === this.STATE.BUFFERING && (this._playing = !1, this._emit(h.START_BUFFERING), this._bufferTimeout = setTimeout(function () {
					this._emitError(r.STREAM.MEDIA_NOT_AVAILABLE, "No media available"), this.pause(this.state)
				}, A))
			}.bind(this), 2e3))
		}, R._onStopBuffering = function () {
			clearTimeout(this._loadingTimeout), clearTimeout(this._bufferTimeout), this._playing = !0, this._setState(this.STATE.PLAYING), this._emit(h.STOP_BUFFERING)
		}, R._onPause = function () {
			clearTimeout(this._loadingTimeout), clearTimeout(this._bufferTimeout), this._playing = !1;
			var e = this._getPauseReason(this._triggered);
			y("nanoplayer (" + this._playerDivId + '): trigger pause with reason "' + e + '", state "' + this._getState() + '"', 3), this._setState(this.STATE.PAUSED), this._stopIntervals(), this._emit(h.PAUSE, {
				reason: e
			})
		}, R._emitMetaData = function (e, t) {
			if (this._metaDataEnabled) {
				y("nanoplayer (" + this._playerDivId + "): onMetaData", 2);
				try {
					var n = JSON.stringify(t);
					y("nanoplayer (" + this._playerDivId + "): handlerName=" + e + ", metaData=" + n, 2)
				} catch (e) {}
				var i = {
					handlerName: e,
					streamTime: 0,
					message: t
				};
				this._emit(h.METADATA, i)
			}
		}, R._onMetaData = function (e) {
			e.width && e.height && this._onStreamInfo({
				onStreamInfo: {
					videoInfo: {
						width: e.width,
						height: e.height
					}
				}
			}), this._emitMetaData("MetaData", e)
		}, R._onCuePoint = function (e) {
			this._emitMetaData("CuePoint", e)
		}, R._onVolumeChange = function () {}, R._onResize = function () {
			if (this._streamInfo && this._streamInfo.haveVideo) {
				this._setMediaElement();
				var e = this._streamInfo.videoInfo.width,
					t = this._streamInfo.videoInfo.height,
					n = this._mediaElement.GetCurrentSizeOfContainer(),
					i = n[0] / n[1],
					r = e / t;
				i < r ? (e = n[0], t = Math.round(e / r)) : (t = n[1], e = Math.round(t * r)), this._mediaElement.SetResize("true"), this._mediaElement.SetVideoDisplayResolution(e + "," + t), this._mediaElement.SetResize("false"), this._mediaElement.SetOnlyThisResolution(e + "," + t), this._resizeTimeout && clearTimeout(this._resizeTimeout), this._resizeTimeout = setTimeout(function () {
					this._resizeTimeout = 0, this._mediaElement.SetResize("true"), this._mediaElement.SetVideoDisplayResolution(e + "," + t), this._mediaElement.SetResize("false"), this._mediaElement.SetOnlyThisResolution(e + "," + t)
				}.bind(this), 300)
			}
		}, R._onDebug = function (e) {
			var t = "string" == typeof e ? e : e.message ? e.message : e.data.message ? e.data.message : "unknown";
			y("nanoplayer (" + this._playerDivId + "): " + t, 2)
		}, R._onFlashReady = function () {
			this._onDebug("flash ready")
		}, R._onFlashState = function (e, t) {
			if ("undefined" != typeof e) {
				var n = "";
				if ("string" == typeof e || e instanceof String) n = e, this._onDebug("onFlashCallState: " + n + " from " + t);
				else {
					this._onDebug("onFlashCallState: " + t);
					for (var i in e) e.hasOwnProperty(i) && (this._onDebug("onFlashCallState: " + i + " = " + e[i]), "state" !== i && "code" !== i || (n = e[i]))
				}
				n === this.FLASH_STATE.VIDEO_DISPLAY.LOADING || n === this.FLASH_STATE.NET_STREAM.PLAY.RESET || n === this.FLASH_STATE.NET_STREAM.PLAY.UNPUBLISH_NOTIFY || n === this.FLASH_STATE.NET_STREAM.PLAY.PUBLISH_NOTIFY ? this._onLoading() : n === this.FLASH_STATE.VIDEO_DISPLAY.REWINDING || n === this.FLASH_STATE.VIDEO_DISPLAY.PLAYING || n === this.FLASH_STATE.NET_STREAM.PLAY.START ? this._playing ? this._onPlaying() : this._onStopBuffering() : n === this.FLASH_STATE.VIDEO_DISPLAY.BUFFERING ? this._onStartBuffering() : n !== this.FLASH_STATE.VIDEO_DISPLAY.CONNECTION_ERROR && n !== this.FLASH_STATE.VIDEO_DISPLAY.STOPPED && n !== this.FLASH_STATE.VIDEO_DISPLAY.DISCONNECTED && n !== this.FLASH_STATE.NET_STREAM.PLAY.STOP && n !== this.FLASH_STATE.NET_STREAM.PLAY.STREAM_NOT_FOUND || this._onPause(), "error" === e.level ? this._onDebug("onFlashCallState: ERROR - " + n) : this._onDebug("onFlashCallState: state=" + n)
			}
		}, R._setName = function () {
			this._setMediaElement(), this._mediaElement.SetName(this._mediaElementId)
		}, R.STATE = p, R.FLASH_STATE = E, R.PAUSE_REASON = m, window._flashPlayers = {}, window.onFlashCallExternalApplicationReady = function () {
			return !0
		}, window.onFlashCallInternalApplicationReady = function (e) {
			window._flashPlayers[e] && window._flashPlayers[e]._onFlashReady()
		}, window.onFlashCallMyTrace = function (e, t) {
			window._flashPlayers[e] && window._flashPlayers[e]._onDebug(t)
		}, window.onFlashCallAutoResizeContainer = function (e, t) {
			window._flashPlayers[e] && window._flashPlayers[e]._onResize(t)
		}, window.onFlashCallVolumeChange = function (e, t) {
			window._flashPlayers[e] && window._flashPlayers[e]._onVolumeChange(t)
		}, window.onFlashCallMetaDataReceived = function (e, t) {
			window._flashPlayers[e] && window._flashPlayers[e]._onMetaData(t)
		}, window.onFlashCallCuePointReceived = function (e, t) {
			window._flashPlayers[e] && window._flashPlayers[e]._onCuePoint(t)
		}, window.onFlashCallState = function (e, t, n) {
			window._flashPlayers[e] && window._flashPlayers[e]._onFlashState(t, n)
		}, T
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			EMBED_PLAYER: 'Could not embed player. Please check the path to the player in the config param "playback.flashplayer" or copy to base directory.'
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		/**
		 * @license
		 * SWFObject v2.3.20130521 <http://github.com/swfobject/swfobject> is released under the MIT License <http://www.opensource.org/licenses/mit-license.php>
		 */
		var e = function () {
			function t() {
				if (!K && document.getElementsByTagName("body")[0]) {
					try {
						var e, t = _("span");
						t.style.display = "none", e = k.getElementsByTagName("body")[0].appendChild(t), e.parentNode.removeChild(e), e = null, t = null
					} catch (e) {
						return
					}
					K = !0;
					for (var n = Y.length, i = 0; i < n; i++) Y[i]()
				}
			}

			function n(e) {
				K ? e() : Y[Y.length] = e
			}

			function i(e) {
				if (typeof F.addEventListener !== P) F.addEventListener("load", e, !1);
				else if (typeof k.addEventListener !== P) k.addEventListener("load", e, !1);
				else if (typeof F.attachEvent !== P) y(F, "onload", e);
				else if ("function" == typeof F.onload) {
					var t = F.onload;
					F.onload = function () {
						t(), e()
					}
				} else F.onload = e
			}

			function r() {
				var e = B.length;
				if (e > 0)
					for (var t = 0; t < e; t++) {
						var n = B[t].id,
							i = B[t].callbackFn,
							r = {
								success: !1,
								id: n
							};
						if (X.pv[0] > 0) {
							var u = m(n);
							if (u)
								if (!v(B[t].swfVersion) || X.wk && X.wk < 312)
									if (B[t].expressInstall && a()) {
										var l = {};
										l.data = B[t].expressInstall, l.width = u.getAttribute("width") || "0", l.height = u.getAttribute("height") || "0", u.getAttribute("class") && (l.styleclass = u.getAttribute("class")), u.getAttribute("align") && (l.align = u.getAttribute("align"));
										for (var d = {}, f = u.getElementsByTagName("param"), h = f.length, E = 0; E < h; E++) "movie" !== f[E].getAttribute("name").toLowerCase() && (d[f[E].getAttribute("name")] = f[E].getAttribute("value"));
										s(l, d, n, i)
									} else c(u), i && i(r);
							else S(n, !0), i && (r.success = !0, r.ref = o(n), r.id = n, i(r))
						} else if (S(n, !0), i) {
							var p = o(n);
							p && typeof p.SetVariable !== P && (r.success = !0, r.ref = p, r.id = p.id), i(r)
						}
					}
			}

			function o(e) {
				var t = null,
					n = m(e);
				return n && "OBJECT" === n.nodeName.toUpperCase() && (t = typeof n.SetVariable !== P ? n : n.getElementsByTagName(D)[0] || n), t
			}

			function a() {
				return !j && v("6.0.65") && (X.win || X.mac) && !(X.wk && X.wk < 312)
			}

			function s(e, t, n, i) {
				var r = m(n);
				if (n = p(n), j = !0, R = i || null, O = {
						success: !1,
						id: n
					}, r) {
					"OBJECT" === r.nodeName.toUpperCase() ? (N = u(r), I = null) : (N = r, I = n), e.id = M, (typeof e.width === P || !/%$/.test(e.width) && T(e.width) < 310) && (e.width = "310"), (typeof e.height === P || !/%$/.test(e.height) && T(e.height) < 137) && (e.height = "137");
					var o = X.ie ? "ActiveX" : "PlugIn",
						a = "MMredirectURL=" + encodeURIComponent(F.location.toString().replace(/&/g, "%26")) + "&MMplayerType=" + o + "&MMdoctitle=" + encodeURIComponent(k.title.slice(0, 47) + " - Flash Player Installation");
					if (typeof t.flashvars !== P ? t.flashvars += "&" + a : t.flashvars = a, X.ie && 4 !== r.readyState) {
						var s = _("div");
						n += "SWFObjectNew", s.setAttribute("id", n), r.parentNode.insertBefore(s, r), r.style.display = "none", h(r)
					}
					d(e, t, n)
				}
			}

			function c(e) {
				if (X.ie && 4 !== e.readyState) {
					e.style.display = "none";
					var t = _("div");
					e.parentNode.insertBefore(t, e), t.parentNode.replaceChild(u(e), t), h(e)
				} else e.parentNode.replaceChild(u(e), e)
			}

			function u(e) {
				var t = _("div");
				if (X.win && X.ie) t.innerHTML = e.innerHTML;
				else {
					var n = e.getElementsByTagName(D)[0];
					if (n) {
						var i = n.childNodes;
						if (i)
							for (var r = i.length, o = 0; o < r; o++) 1 === i[o].nodeType && "PARAM" === i[o].nodeName || 8 === i[o].nodeType || t.appendChild(i[o].cloneNode(!0))
					}
				}
				return t
			}

			function l(e, t) {
				var n = _("div");
				return n.innerHTML = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'><param name='movie' value='" + e + "'>" + t + "</object>", n.firstChild
			}

			function d(e, t, n) {
				var i, r = m(n);
				if (n = p(n), X.wk && X.wk < 312) return i;
				if (r) {
					var o, a, s, c = _(X.ie ? "div" : D);
					typeof e.id === P && (e.id = n);
					for (s in t) t.hasOwnProperty(s) && "movie" !== s.toLowerCase() && f(c, s, t[s]);
					X.ie && (c = l(e.data, c.innerHTML));
					for (o in e) e.hasOwnProperty(o) && (a = o.toLowerCase(), "styleclass" === a ? c.setAttribute("class", e[o]) : "classid" !== a && "data" !== a && c.setAttribute(o, e[o]));
					X.ie ? H[H.length] = e.id : (c.setAttribute("type", U), c.setAttribute("data", e.data)), r.parentNode.replaceChild(c, r), i = c
				}
				return i
			}

			function f(e, t, n) {
				var i = _("param");
				i.setAttribute("name", t), i.setAttribute("value", n), e.appendChild(i)
			}

			function h(e) {
				var t = m(e);
				t && "OBJECT" === t.nodeName.toUpperCase() && (X.ie ? (t.style.display = "none", function e() {
					if (4 === t.readyState) {
						for (var n in t) "function" == typeof t[n] && (t[n] = null);
						t.parentNode.removeChild(t)
					} else setTimeout(e, 10)
				}()) : t.parentNode.removeChild(t))
			}

			function E(e) {
				return e && e.nodeType && 1 === e.nodeType
			}

			function p(e) {
				return E(e) ? e.id : e
			}

			function m(e) {
				if (E(e)) return e;
				var t = null;
				try {
					t = k.getElementById(e)
				} catch (e) {}
				return t
			}

			function _(e) {
				return k.createElement(e)
			}

			function T(e) {
				return parseInt(e, 10)
			}

			function y(e, t, n) {
				e.attachEvent(t, n), W[W.length] = [e, t, n]
			}

			function v(e) {
				e += "";
				var t = X.pv,
					n = e.split(".");
				return n[0] = T(n[0]), n[1] = T(n[1]) || 0, n[2] = T(n[2]) || 0, !!(t[0] > n[0] || t[0] === n[0] && t[1] > n[1] || t[0] === n[0] && t[1] === n[1] && t[2] >= n[2])
			}

			function g(e, t, n, i) {
				var r = k.getElementsByTagName("head")[0];
				if (r) {
					var o = "string" == typeof n ? n : "screen";
					if (i && (C = null, b = null), !C || b !== o) {
						var a = _("style");
						a.setAttribute("type", "text/css"), a.setAttribute("media", o), C = r.appendChild(a), X.ie && typeof k.styleSheets !== P && k.styleSheets.length > 0 && (C = k.styleSheets[k.styleSheets.length - 1]), b = o
					}
					C && (typeof C.addRule !== P ? C.addRule(e, t) : typeof k.createTextNode !== P && C.appendChild(k.createTextNode(e + " {" + t + "}")))
				}
			}

			function S(e, t) {
				if (z) {
					var n = t ? "visible" : "hidden",
						i = m(e);
					K && i ? i.style.visibility = n : "string" == typeof e && g("#" + e, "visibility:" + n)
				}
			}

			function A(e) {
				var t = /[\\"<>.;]/,
					n = null !== t.exec(e);
				return n && typeof encodeURIComponent !== P ? encodeURIComponent(e) : e
			}
			var N, I, R, O, C, b, P = "undefined",
				D = "object",
				w = "Shockwave Flash",
				L = "ShockwaveFlash.ShockwaveFlash",
				U = "application/x-shockwave-flash",
				M = "SWFObjectExprInst",
				x = "onreadystatechange",
				F = window,
				k = document,
				G = navigator,
				V = !1,
				Y = [],
				B = [],
				H = [],
				W = [],
				K = !1,
				j = !1,
				z = !0,
				q = !1,
				X = function () {
					var e = typeof k.getElementById !== P && typeof k.getElementsByTagName !== P && typeof k.createElement !== P,
						t = G.userAgent.toLowerCase(),
						n = G.platform.toLowerCase(),
						i = n ? /win/.test(n) : /win/.test(t),
						r = n ? /mac/.test(n) : /mac/.test(t),
						o = !!/webkit/.test(t) && parseFloat(t.replace(/^.*webkit\/(\d+(\.\d+)?).*$/, "$1")),
						a = "Microsoft Internet Explorer" === G.appName,
						s = [0, 0, 0],
						c = null;
					if (typeof G.plugins !== P && typeof G.plugins[w] === D) c = G.plugins[w].description, c && typeof G.mimeTypes !== P && G.mimeTypes[U] && G.mimeTypes[U].enabledPlugin && (V = !0, a = !1, c = c.replace(/^.*\s+(\S+\s+\S+$)/, "$1"), s[0] = T(c.replace(/^(.*)\..*$/, "$1")), s[1] = T(c.replace(/^.*\.(.*)\s.*$/, "$1")), s[2] = /[a-zA-Z]/.test(c) ? T(c.replace(/^.*[a-zA-Z]+(.*)$/, "$1")) : 0);
					else if (typeof F.ActiveXObject !== P) try {
						var u = new ActiveXObject(L);
						u && (c = u.GetVariable("$version"), c && (a = !0, c = c.split(" ")[1].split(","), s = [T(c[0]), T(c[1]), T(c[2])]))
					} catch (e) {}
					return {
						w3: e,
						pv: s,
						wk: o,
						ie: a,
						win: i,
						mac: r
					}
				}();
			(function () {
				X.w3 && ((typeof k.readyState !== P && ("complete" === k.readyState || "interactive" === k.readyState) || typeof k.readyState === P && (k.getElementsByTagName("body")[0] || k.body)) && t(), K || (typeof k.addEventListener !== P && k.addEventListener("DOMContentLoaded", t, !1), X.ie && (k.attachEvent(x, function e() {
					"complete" === k.readyState && (k.detachEvent(x, e), t())
				}), F == top && ! function e() {
					if (!K) {
						try {
							k.documentElement.doScroll("left")
						} catch (t) {
							return void setTimeout(e, 0)
						}
						t()
					}
				}()), X.wk && ! function e() {
					if (!K) return /loaded|complete/.test(k.readyState) ? void t() : void setTimeout(e, 0)
				}()))
			})();
			return Y[0] = function () {
					V && r()
				},
				function () {
					X.ie && window.attachEvent("onunload", function () {
						for (var t = W.length, n = 0; n < t; n++) W[n][0].detachEvent(W[n][1], W[n][2]);
						for (var i = H.length, r = 0; r < i; r++) h(H[r]);
						for (var o in X) X[o] = null;
						X = null;
						for (var a in e) e[a] = null;
						e = null
					})
				}(), {
					registerObject: function (e, t, n, i) {
						if (X.w3 && e && t) {
							var r = {};
							r.id = e, r.swfVersion = t, r.expressInstall = n, r.callbackFn = i, B[B.length] = r, S(e, !1)
						} else i && i({
							success: !1,
							id: e
						})
					},
					getObjectById: function (e) {
						if (X.w3) return o(e)
					},
					embedSWF: function (e, t, i, r, o, c, u, l, f, h) {
						var E = p(t),
							m = {
								success: !1,
								id: E
							};
						X.w3 && !(X.wk && X.wk < 312) && e && t && i && r && o ? (S(E, !1), n(function () {
							i += "", r += "";
							var n = {};
							if (f && typeof f === D)
								for (var p in f) n[p] = f[p];
							n.data = e, n.width = i, n.height = r;
							var _ = {};
							if (l && typeof l === D)
								for (var T in l) _[T] = l[T];
							if (u && typeof u === D)
								for (var y in u)
									if (u.hasOwnProperty(y)) {
										var g = q ? encodeURIComponent(y) : y,
											A = q ? encodeURIComponent(u[y]) : u[y];
										typeof _.flashvars !== P ? _.flashvars += "&" + g + "=" + A : _.flashvars = g + "=" + A
									} if (v(o)) {
								var N = d(n, _, t);
								n.id === E && S(E, !0), m.success = !0, m.ref = N, m.id = N.id
							} else {
								if (c && a()) return n.data = c, void s(n, _, t, h);
								S(E, !0)
							}
							h && h(m)
						})) : h && h(m)
					},
					switchOffAutoHideShow: function () {
						z = !1
					},
					enableUriEncoding: function (e) {
						q = typeof e === P || e
					},
					ua: X,
					getFlashPlayerVersion: function () {
						return {
							major: X.pv[0],
							minor: X.pv[1],
							release: X.pv[2]
						}
					},
					hasFlashPlayerVersion: v,
					createSWF: function (e, t, n) {
						return X.w3 ? d(e, t, n) : void 0
					},
					showExpressInstall: function (e, t, n, i) {
						X.w3 && a() && s(e, t, n, i)
					},
					removeSWF: function (e) {
						X.w3 && h(e)
					},
					createCSS: function (e, t, n, i) {
						X.w3 && g(e, t, n, i)
					},
					addDomLoadEvent: n,
					addLoadEvent: i,
					getQueryParamValue: function (e) {
						var t = k.location.search || k.location.hash;
						if (t) {
							if (/\?/.test(t) && (t = t.split("?")[1]), !e) return A(t);
							for (var n = t.split("&"), i = 0; i < n.length; i++)
								if (n[i].substring(0, n[i].indexOf("=")) === e) return A(n[i].substring(n[i].indexOf("=") + 1))
						}
						return ""
					},
					expressInstallCallback: function () {
						if (j) {
							var e = m(M);
							e && N && (e.parentNode.replaceChild(N, e), I && (S(I, !0), X.ie && (N.style.display = "block")), R && R(O)), j = !1
						}
					},
					version: "2.3"
				}
		}();
		return e
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return Object.freeze({
			VIDEO_DISPLAY: {
				CONNECTION_ERROR: "connectionError",
				LOADING: "loading",
				PLAYING: "playing",
				REWINDING: "rewinding",
				BUFFERING: "buffering",
				STOPPED: "stopped",
				DISCONNECTED: "disconnected"
			},
			NET_STREAM: {
				PLAY: {
					RESET: "NetStream.Play.Reset",
					UNPUBLISH_NOTIFY: "NetStream.Play.UnpublishNotify",
					PUBLISH_NOTIFY: "NetStream.Play.PublishNotify",
					PLAY: "NetStream.Play.Start",
					STOP: "NetStream.Play.Stop",
					STREAM_NOT_FOUND: "NetStream.Play.StreamNotFound"
				}
			}
		})
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		return {
			FORCE_NOT_SUPPORTED: 'The forced tech "$tech$" is not supported by your browser.',
			SOURCE_NOT_SUPPORTED: "The players source configuration is malformed or missing, please check documentation for more information or contact us.",
			CLIENT_NOT_SUPPORTED: "This browser does not fully support HTML5 and H5Live.\t            Supported are: Chrome >=54 (Windows, MacOSX, Android), Firefox >=48 (Windows, MacOSX, Android), Microsoft Edge (Windows), Microsoft Internet Explorer 11 (at least Windows 8), Safari (MacOSX & at least iOS 10)."
		}
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}, function (e, t, n) {
	var i, r;
	i = [], r = function () {
		function e() {}

		function t(e, t) {
			return function () {
				e.apply(t, arguments)
			}
		}

		function n(e) {
			if ("object" != typeof this) throw new TypeError("Promises must be constructed via new");
			if ("function" != typeof e) throw new TypeError("not a function");
			this._state = 0, this._handled = !1, this._value = void 0, this._deferreds = [], c(e, this)
		}

		function i(e, t) {
			for (; 3 === e._state;) e = e._value;
			return 0 === e._state ? void e._deferreds.push(t) : (e._handled = !0, void n._immediateFn(function () {
				var n = 1 === e._state ? t.onFulfilled : t.onRejected;
				if (null === n) return void(1 === e._state ? r : o)(t.promise, e._value);
				var i;
				try {
					i = n(e._value)
				} catch (e) {
					return void o(t.promise, e)
				}
				r(t.promise, i)
			}))
		}

		function r(e, i) {
			try {
				if (i === e) throw new TypeError("A promise cannot be resolved with itself.");
				if (i && ("object" == typeof i || "function" == typeof i)) {
					var r = i.then;
					if (i instanceof n) return e._state = 3, e._value = i, void a(e);
					if ("function" == typeof r) return void c(t(r, i), e)
				}
				e._state = 1, e._value = i, a(e)
			} catch (t) {
				o(e, t)
			}
		}

		function o(e, t) {
			e._state = 2, e._value = t, a(e)
		}

		function a(e) {
			2 === e._state && 0 === e._deferreds.length && n._immediateFn(function () {
				e._handled || n._unhandledRejectionFn(e._value)
			});
			for (var t = 0, r = e._deferreds.length; t < r; t++) i(e, e._deferreds[t]);
			e._deferreds = null
		}

		function s(e, t, n) {
			this.onFulfilled = "function" == typeof e ? e : null, this.onRejected = "function" == typeof t ? t : null, this.promise = n
		}

		function c(e, t) {
			var n = !1;
			try {
				e(function (e) {
					n || (n = !0, r(t, e))
				}, function (e) {
					n || (n = !0, o(t, e))
				})
			} catch (e) {
				if (n) return;
				n = !0, o(t, e)
			}
		}
		var u = setTimeout;
		return n.prototype.catch = function (e) {
			return this.then(null, e)
		}, n.prototype.then = function (t, n) {
			var r = new this.constructor(e);
			return i(this, new s(t, n, r)), r
		}, n.all = function (e) {
			var t = Array.prototype.slice.call(e);
			return new n(function (e, n) {
				function i(o, a) {
					try {
						if (a && ("object" == typeof a || "function" == typeof a)) {
							var s = a.then;
							if ("function" == typeof s) return void s.call(a, function (e) {
								i(o, e)
							}, n)
						}
						t[o] = a, 0 === --r && e(t)
					} catch (e) {
						n(e)
					}
				}
				if (0 === t.length) return e([]);
				for (var r = t.length, o = 0; o < t.length; o++) i(o, t[o])
			})
		}, n.resolve = function (e) {
			return e && "object" == typeof e && e.constructor === n ? e : new n(function (t) {
				t(e)
			})
		}, n.reject = function (e) {
			return new n(function (t, n) {
				n(e)
			})
		}, n.race = function (e) {
			return new n(function (t, n) {
				for (var i = 0, r = e.length; i < r; i++) e[i].then(t, n)
			})
		}, n._immediateFn = "function" == typeof window.setImmediate && function (e) {
			window.setImmediate(e)
		} || function (e) {
			u(e, 0)
		}, n._unhandledRejectionFn = function (e) {
			"undefined" != typeof console && console && console.warn("Possible Unhandled Promise Rejection:", e)
		}, n._setImmediateFn = function (e) {
			n._immediateFn = e
		}, n._setUnhandledRejectionFn = function (e) {
			n._unhandledRejectionFn = e
		}, void 0 === window.Promise && (window.Promise = n), window.Promise
	}.apply(t, i), !(void 0 !== r && (e.exports = r))
}]);