var openLog=false;

console.write=function (str) {
  if(openLog){
    console.log(str);
  }
}

function H5sPlayerWS(t) {
  this.sourceBuffer, this.buffer = [], this.t, this.s, this.i, this.o, this.h, this.l = 0, this.u = 0, this.S = 0, this.v = !1, this.p = !1, this.H = !1, this.C, this.k = t, console.write("Websocket Conf:", t), this.P = t.videoid, this.W = t.token, void 0 === this.P ? (this.R = t.videodom, console.write(t.token, "use dom directly")) : (this.R = document.getElementById(this.P), console.write(t.token, "use videoid")), this.s = this.R;
  var s = this.k.protocol + "//" + this.k.host + this.k.rootpath + "api/v1/GetImage?token=" + this.W + "&session=" + this.k.session;
  this.R.setAttribute("poster", s)
}

function H5sPlayerHls(t) {
  this.i, this.h, this.k = t, this.P = t.videoid, this.W = t.token, this.I, this.T = t.hlsver, void 0 === this.P ? (this.R = t.videodom, console.write(t.token, "use dom directly")) : (this.R = document.getElementById(this.P), console.write(t.token, "use videoid")), this.m = this.R, this.m.type = "application/x-mpegURL", this.M = 0, this.O = 0;
  var s = this.k.protocol + "//" + window.location.host + "/api/v1/GetImage?token=" + this.W + "&session=" + this.k.session;
  this.R.setAttribute("poster", s)
}

function H5sPlayerRTC(t) {
  this.i, this.o, this.h, this.l = 0, this.u = 0, this.S = 0, this.v = !1, this.p = !1, this.k = t, this.P = t.videoid, this.W = t.token, void 0 === this.P ? (this.R = t.videodom, console.write(t.token, "use dom directly")) : (this.R = document.getElementById(this.P), console.write(t.token, "use videoid")), this.s = this.R, this.J = null, this.N = {optional: [{DtlsSrtpKeyAgreement: !0}]}, this.g = {
    mandatory: {
      offerToReceiveAudio: !0,
      offerToReceiveVideo: !0
    }
  }, this.A = {L: []}, this.B = [];
  var s = this.k.protocol + "//" + this.k.host + this.k.rootpath + "api/v1/GetImage?token=" + this.W + "&session=" + this.k.session;
  this.R.setAttribute("poster", s)
}

function createRTCSessionDescription(t) {
  return console.write("createRTCSessionDescription "), new RTCSessionDescription(t)
}

H5sPlayerWS.prototype.D = function () {
  console.write("Try Reconnect...", this.v), !0 === this.v && (console.write("Reconnect..."), this.G(this.W), this.v = !1), console.write("Try Reconnect...", this.v)
}, H5sPlayerWS.prototype.U = function (t) {
  var s;
  console.write("H5SWebSocketClient");
  try {
    "http:" == this.k.protocol && (s = "undefined" != typeof MozWebSocket ? new MozWebSocket("ws://" + this.k.host + t) : new WebSocket("ws://" + this.k.host + t)), "https:" == this.k.protocol && (console.write(this.k.host), s = "undefined" != typeof MozWebSocket ? new MozWebSocket("wss://" + this.k.host + t) : new WebSocket("wss://" + this.k.host + t)), console.write(this.k.host)
  } catch (t) {
    return void alert("error")
  }
  return s
}, H5sPlayerWS.prototype.K = function () {
  if (null !== this.sourceBuffer && void 0 !== this.sourceBuffer) {
    if (0 !== this.buffer.length && !this.sourceBuffer.updating) try {
      var t = this.buffer.shift(), s = new Uint8Array(t);
      this.sourceBuffer.appendBuffer(s)
    } catch (t) {
      console.write(t)
    }
  } else console.write(this.sourceBuffer, "is null or undefined")
}, H5sPlayerWS.prototype.V = function () {
  try {
    this.i.send("keepalive")
  } catch (t) {
    console.write(t)
  }
}, H5sPlayerWS.prototype.j = function (t) {
  if (!0 !== this.p) return !1 === this.H ? (this.C = String.fromCharCode.apply(null, new Uint8Array(t.data)), this.q(this), void(this.H = !0)) : (this.buffer.push(t.data), void this.K())
}, H5sPlayerWS.prototype.q = function (t) {
  try {
    window.MediaSource = window.MediaSource || window.WebKitMediaSource, window.MediaSource || console.write("MediaSource API is not available");
    var s = 'video/mp4; codecs="avc1.42E01E, mp4a.40.2"';
    "MediaSource" in window && MediaSource.isTypeSupported(s) ? console.write("MIME type or codec: ", s) : console.write("Unsupported MIME type or codec: ", s), t.t = new window.MediaSource, t.s.autoplay = !0, console.write(t.P);
    t.s.src = window.URL.createObjectURL(t.t), t.s.play(), t.t.addEventListener("sourceopen", t.F.bind(t), !1)
  } catch (t) {
    console.write(t)
  }
}, H5sPlayerWS.prototype.F = function () {
  console.write("Add SourceBuffer"), this.sourceBuffer = this.t.addSourceBuffer(this.C), this.t.duration = 1 / 0, this.t.removeEventListener("sourceopen", this.F, !1), this.sourceBuffer.addEventListener("updateend", this.K.bind(this), !1)
}, H5sPlayerWS.prototype.G = function (t) {
  this.s.autoplay = !0;
  var s = "api/v1/h5swsapi";
  s = this.k.rootpath + s + "?token=" + t + "&session=" + this.k.session, console.write(s), this.i = this.U(s), console.write("setupWebSocket", this.i), this.i.binaryType = "arraybuffer", (this.i.X = this).i.onmessage = this.j.bind(this), this.i.onopen = function () {
    console.write("wsSocket.onopen", this.X), this.X.o = setInterval(this.X.Y.bind(this.X), 1e4), this.X.h = setInterval(this.X.V.bind(this.X), 1e3)
  }, this.i.onclose = function () {
    console.write("wsSocket.onclose", this.X), !0 === this.X.p ? console.write("wsSocket.onclose disconnect") : this.X.v = !0, this.X.Z(this.X), this.X.$(this.X), this.X.C = "", this.X.H = !1
  }
}, H5sPlayerWS.prototype.Z = function (t) {
  console.write("Cleanup Source Buffer", t);
  try {
    t.sourceBuffer.removeEventListener("updateend", t.K, !1), t.sourceBuffer.abort(), document.documentMode || /Edge/.test(navigator.userAgent) ? console.write("IE or EDGE!") : t.t.removeSourceBuffer(t.sourceBuffer), t.sourceBuffer = null, t.t = null, t.buffer = []
  } catch (t) {
    console.write(t)
  }
}, H5sPlayerWS.prototype.$ = function (t) {
  console.write("CleanupWebSocket", t), clearInterval(t.h), clearInterval(t.o), t.l = 0, t.u = 0, t.S = 0
}, H5sPlayerWS.prototype.Y = function () {
  !0 === this.p && (console.write("CheckSourceBuffer has been disconnect", this), clearInterval(this.h), clearInterval(this.o), clearInterval(this._));
  try {
    if (console.write("CheckSourceBuffer", this), this.sourceBuffer.buffered.length <= 0) {
      if (this.l++, 8 < this.l) return console.write("CheckSourceBuffer Close 1"), void this.i.close()
    } else {
      this.l = 0;
      this.sourceBuffer.buffered.start(0);
      var t = this.sourceBuffer.buffered.end(0), s = t - this.s.currentTime;
      if (5 < s || s < 0) return console.write("CheckSourceBuffer Close 2", s), void this.i.close();
      if (t == this.u) {
        if (this.S++, 3 < this.S) return console.write("CheckSourceBuffer Close 3"), void this.i.close()
      } else this.S = 0;
      this.u = t
    }
  } catch (t) {
    console.write(t)
  }
}, H5sPlayerWS.prototype.connect = function () {
  this.G(this.W), this._ = setInterval(this.D.bind(this), 3e3)
}, H5sPlayerWS.prototype.disconnect = function () {
  console.write("disconnect", this), this.p = !0, clearInterval(this._), null != this.i && (this.i.close(), this.i = null), console.write("disconnect", this)
}, H5sPlayerHls.prototype.U = function (t) {
  var s;
  console.write("H5SWebSocketClient");
  try {
    "http:" == this.k.protocol && (s = "undefined" != typeof MozWebSocket ? new MozWebSocket("ws://" + this.k.host + t) : new WebSocket("ws://" + this.k.host + t)), "https:" == this.k.protocol && (console.write(this.k.host), s = "undefined" != typeof MozWebSocket ? new MozWebSocket("wss://" + this.k.host + t) : new WebSocket("wss://" + this.k.host + t)), console.write(this.k.host)
  } catch (t) {
    return void alert("error")
  }
  return s
}, H5sPlayerHls.prototype.V = function () {
  try {
    var t = {type: "keepalive"};
    this.i.send(JSON.stringify(t))
  } catch (t) {
    console.write(t)
  }
}, H5sPlayerHls.prototype.j = function (t) {
  console.write("HLS received ", t.data)
}, H5sPlayerHls.prototype.G = function (t) {
  var s = "api/v1/h5swscmnapi";
  s = this.k.rootpath + s + "?token=" + t, console.write(s), this.i = this.U(s), console.write("setupWebSocket", this.i), this.i.binaryType = "arraybuffer", (this.i.X = this).i.onmessage = this.j.bind(this), this.i.onopen = function () {
    console.write("wsSocket.onopen", this.X), this.X.h = setInterval(this.X.V.bind(this.X), 1e3)
  }, this.i.onclose = function () {
    console.write("wsSocket.onclose", this.X), this.X.$(this.X)
  }
}, H5sPlayerHls.prototype.$ = function (t) {
  console.write("H5sPlayerHls CleanupWebSocket", t), clearInterval(t.h)
}, H5sPlayerHls.prototype.tt = function () {
  console.write("HLS video.ended", this.m.ended), console.write("HLS video.currentTime", this.m.currentTime);
  var t = this.m.currentTime, s = t - this.M;
  console.write("HLS diff", s), 0 === s && this.O++, this.M = t, 3 < this.O && (null != this.i && (this.i.close(), this.i = null), this.G(this.W), console.write("HLS reconnect"), this.m.src = "", this.M = 0, this.O = 0, this.m.src = this.k.protocol + "//" + this.k.host + this.k.rootpath + "hls/" + this.T + "/" + this.W + "/hls.m3u8", this.m.play())
}, H5sPlayerHls.prototype.connect = function () {
  this.G(this.W), this.M = 0, this.O = 0, this.m.onended = function (t) {
    console.write("The End")
  }, this.m.onpause = function (t) {
    console.write("Pause")
  }, this.m.onplaying = function (t) {
    console.write("Playing")
  }, this.m.onseeking = function (t) {
    console.write("seeking")
  }, this.m.onvolumechange = function (t) {
    console.write("volumechange")
  }, this.m.src = this.k.protocol + "//" + this.k.host + this.k.rootpath + "hls/" + this.T + "/" + this.W + "/hls.m3u8", this.m.play(), this.I = setInterval(this.tt.bind(this), 3e3)
}, H5sPlayerHls.prototype.disconnect = function () {
  clearInterval(this.I), this.M = 0, this.O = 0, null != this.i && (this.i.close(), this.i = null), console.write("disconnect", this)
}, H5sPlayerRTC.prototype.D = function () {
  console.write("Try Reconnect...", this.v), !0 === this.v && (console.write("Reconnect..."), this.G(this.W), this.v = !1), console.write("Try Reconnect...", this.v)
}, H5sPlayerRTC.prototype.U = function (t) {
  var s;
  console.write("H5SWebSocketClient");
  try {
    "http:" == this.k.protocol && (s = "undefined" != typeof MozWebSocket ? new MozWebSocket("ws://" + this.k.host + t) : new WebSocket("ws://" + this.k.host + t)), "https:" == this.k.protocol && (console.write(this.k.host), s = "undefined" != typeof MozWebSocket ? new MozWebSocket("wss://" + this.k.host + t) : new WebSocket("wss://" + this.k.host + t)), console.write(this.k.host)
  } catch (t) {
    return void alert("error")
  }
  return s
}, H5sPlayerRTC.prototype.V = function () {
  try {
    var t = {type: "keepalive"};
    this.i.send(JSON.stringify(t))
  } catch (t) {
    console.write(t)
  }
}, H5sPlayerRTC.prototype.st = function (t) {
  if (t.candidate) {
    var s;
    console.write("onIceCandidate currentice", t.candidate), s = t.candidate, console.write("onIceCandidate currentice", s, JSON.stringify(s));
    var e = JSON.parse(JSON.stringify(s));
    e.type = "remoteice", console.write("onIceCandidate currentice new", e, JSON.stringify(e)), this.i.send(JSON.stringify(e))
  } else console.write("End of candidates.")
}, H5sPlayerRTC.prototype.et = function (t) {
  var s;
  console.write("Remote track added:" + JSON.stringify(t)), s = t.it ? t.it[0] : t.stream;
  var e = this.R;
  e.src = URL.createObjectURL(s), e.play()
}, H5sPlayerRTC.prototype.ot = function () {
  console.write("createPeerConnection  config: " + JSON.stringify(this.A) + " option:" + JSON.stringify(this.N));
  var s = new RTCPeerConnection(this.A, this.N), e = this;
  return s.onicecandidate = function (t) {
    e.st.call(e, t)
  }, void 0 !== s.nt ? s.nt = function (t) {
    e.et.call(e, t)
  } : s.onaddstream = function (t) {
    e.et.call(e, t)
  }, s.oniceconnectionstatechange = function (t) {
    console.write("oniceconnectionstatechange  state: " + s.iceConnectionState)
  }, console.write("Created RTCPeerConnnection with config: " + JSON.stringify(this.A) + "option:" + JSON.stringify(this.N)), s
}, H5sPlayerRTC.prototype.ht = function (t) {
  console.write("ProcessRTCOffer", t);
  try {
    this.J = this.ot(), this.B.length = 0;
    var s = this;
    this.J.setRemoteDescription(createRTCSessionDescription(t)), this.J.createAnswer(this.g).then(function (t) {
      console.write("Create answer:" + JSON.stringify(t)), s.J.setLocalDescription(t, function () {
        console.write("ProcessRTCOffer createAnswer", t), s.i.send(JSON.stringify(t))
      }, function () {
      })
    }, function (t) {
      alert("Create awnser error:" + JSON.stringify(t))
    })
  } catch (t) {
    this.disconnect(), alert("connect error: " + t)
  }
}, H5sPlayerRTC.prototype.ct = function (t) {
  console.write("ProcessRemoteIce", t);
  try {
    var s = new RTCIceCandidate({sdpMLineIndex: t.sdpMLineIndex, candidate: t.candidate});
    console.write("ProcessRemoteIce", s), console.write("Adding ICE candidate :" + JSON.stringify(s)), this.J.addIceCandidate(s, function () {
      console.write("addIceCandidate OK")
    }, function (t) {
      console.write("addIceCandidate error:" + JSON.stringify(t))
    })
  } catch (t) {
    alert("connect ProcessRemoteIce error: " + t)
  }
}, H5sPlayerRTC.prototype.j = function (t) {
  console.write("RTC received ", t.data);
  var s = JSON.parse(t.data);
  console.write("Get Message type ", s.type), "offer" === s.type && (console.write("Process Message type ", s.type), this.ht(s)), "remoteice" === s.type && (console.write("Process Message type ", s.type), this.ct(s))
}, H5sPlayerRTC.prototype.G = function (t) {
  this.s.autoplay = !0;
  var s = "api/v1/h5srtcapi";
  s = this.k.rootpath + s + "?token=" + t, console.write(s), this.i = this.U(s), console.write("setupWebSocket", this.i), this.i.binaryType = "arraybuffer", (this.i.X = this).i.onmessage = this.j.bind(this), this.i.onopen = function () {
    console.write("wsSocket.onopen", this.X);
    var t = {type: "open"};
    this.X.i.send(JSON.stringify(t)), this.X.h = setInterval(this.X.V.bind(this.X), 1e3)
  }, this.i.onclose = function () {
    console.write("wsSocket.onclose", this.X), !0 === this.X.p ? console.write("wsSocket.onclose disconnect") : this.X.v = !0, this.X.$(this.X)
  }
}, H5sPlayerRTC.prototype.$ = function (t) {
  console.write("CleanupWebSocket", t), clearInterval(t.h), t.l = 0, t.u = 0, t.S = 0
}, H5sPlayerRTC.prototype.connect = function () {
  this.G(this.W), this._ = setInterval(this.D.bind(this), 3e3)
}, H5sPlayerRTC.prototype.disconnect = function () {
  console.write("disconnect", this), this.p = !0, clearInterval(this._), null != this.i && (this.i.close(), this.i = null), console.write("disconnect", this)
};
