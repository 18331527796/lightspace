webpackJsonp([1],{"+/TY":function(e,t){},NHnr:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=n("7+uW"),s={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},staticRenderFns:[]};var o=n("VU/8")({name:"App"},s,!1,function(e){n("ymcE")},null,null).exports,a=n("zL8q"),r=n.n(a),c=n("/ocq"),l=n("mtWM"),d=n.n(l),u={created:function(){this.getMenuList()},data:function(){return{isCollapse:!1,activeIndex:"/singleFocus",menuList:[]}},methods:{goto:function(e){window.sessionStorage.setItem("labelId",e)},getMenuList:function(){var e=new FormData;d()({method:"post",url:"/lightspace/priceclient/getAllLabel",data:e}).then(this.handleGetMenuSucc.bind(this)).catch(function(e){console.log(e)})},handleGetMenuSucc:function(e){200==e.data.status&&(this.menuList=e.data.data)},toggleCollapse:function(){this.isCollapse=!this.isCollapse},saveNavState:function(e,t){this.activeIndex=t,this.$store.commit("changeLabel",e),window.sessionStorage.setItem("labelId",e)}}},h={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("el-container",{staticClass:"home-container"},[n("el-aside",{staticStyle:{"min-height":"100vh"},attrs:{width:e.isCollapse?"0.64rem":"2rem"}},[n("el-menu",{staticClass:"el-menu-vertical-demo",attrs:{"default-active":this.$route.path,"background-color":"#333744","text-color":"#fff","active-text-color":"#409eff",router:"","unique-opened":!0,collapse:e.isCollapse,"collapse-transition":!1}},e._l(e.menuList,function(t){return n("el-menu-item",{key:t.id,attrs:{index:"/"+t.path},on:{click:function(n){return e.saveNavState(t.id,"/"+t.path)}}},[n("i",{staticClass:"el-icon-set-up"}),e._v(" "),n("span",{attrs:{slot:"title"},slot:"title"},[e._v(e._s(t.name))])])}),1)],1),e._v(" "),n("el-main",[n("router-view")],1)],1)],1)},staticRenderFns:[]};var f=n("VU/8")(u,h,!1,function(e){n("bojw")},"data-v-3e8dd8be",null).exports,p={created:function(){var e=window.sessionStorage.getItem("labelId");this.selectedOne=window.sessionStorage.getItem("oneSelected"),e||(e=3),this.getSeries(e)},data:function(){return{checked:!0,num:0,selectedOne:"",seriesList:[],selectedArr:[],disable:!1}},watch:{labelId:function(e,t){e!==t&&this.getSeries(e)}},computed:{labelId:function(){return this.$store.state.labelId}},methods:{seeDifferent:function(){var e=this.$router.resolve({path:"/different",query:{one:this.selectedArr[0],two:this.selectedArr[1]}});window.open(e.href,"_blank"),this.clearChoose()},clearChoose:function(){this.$router.go(0),this.disable=!1,window.sessionStorage.setItem("oneSelected","")},getChooseItem:function(e,t){var n=this;this.selectedOne&&(this.selectedArr=[]),this.seriesList.forEach(function(i,s){i.id==e&&(i.checked=!t,i.checked&&n.selectedArr.push(i.id))}),1==this.selectedArr.length&&window.sessionStorage.setItem("oneSelected",this.selectedArr[0]),this.disable=this.selectedArr.length>=2},openFullScreen:function(){return this.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0,0,0,0.7)"})},closeFullScreen:function(e){e.close()},getSeries:function(e){this.openFullScreen();var t=new FormData;t.append("id",e),d()({method:"post",url:"/lightspace/priceclient/getSeriesByLabel",data:t}).then(this.handleGetSeriesSucc.bind(this)).catch(function(e){console.log(e)})},handleGetSeriesSucc:function(e){e.data.data&&(this.closeFullScreen(this.openFullScreen()),this.seriesList=e.data.data,this.seriesList.forEach(function(e,t){e.checked=!1}))},gotoDetail:function(e){var t=this.$router.resolve({path:"/detail",query:{id:e}});window.open(t.href,"_blank")}}},m={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("el-row",{staticStyle:{"margin-bottom":".2rem"}},[n("el-col",{attrs:{span:4,offset:20}},[n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.seeDifferent}},[e._v("查看对比")]),e._v(" "),n("el-button",{attrs:{type:"success",size:"mini"},on:{click:e.clearChoose}},[e._v("清空选项")])],1)],1),e._v(" "),n("div",{staticClass:"seriesWrap"},e._l(e.seriesList,function(t){return n("div",{key:t.id,staticClass:"itemWrap"},[n("el-checkbox",{attrs:{checked:t.checked,disabled:e.disable},on:{change:function(n){return e.getChooseItem(t.id,t.checked)}}},[e._v("选择对比")]),e._v(" "),n("el-image",{staticClass:"imgBox",staticStyle:{"border-radius":".2rem"},attrs:{src:t.introduce,fit:"contain"},on:{click:function(n){return e.gotoDetail(t.id)}}}),e._v(" "),n("div",{staticClass:"title"},[e._v(e._s(t.name))])],1)}),0)],1)},staticRenderFns:[]};var g=n("VU/8")(p,m,!1,function(e){n("lI/0")},"data-v-726241b4",null).exports,v={created:function(){var e=this;this.id=this.$router.history.current.query.id,this.getSwiper(),this.$nextTick(function(){e.bannerH=.8*document.body.clientHeight,e.bannerW=.8*document.body.clientHeight/2*3})},mounted:function(){var e=this;window.addEventListener("resize",function(){e.setBannerH()},!1)},data:function(){return{id:"",swiperList:[],bannerH:0,bannerW:0}},methods:{openFullScreen:function(){return this.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0,0,0,0.7)"})},closeFullScreen:function(e){e.close()},setBannerH:function(){this.bannerH=.8*document.body.clientHeight,this.bannerW=.8*document.body.clientHeight/2*3},getSwiper:function(){this.openFullScreen();var e=new FormData;e.append("id",this.id),d()({method:"post",url:"/lightspace/priceclient/getProduceBySeries",data:e}).then(this.handleGetSwiperSucc.bind(this)).catch(function(e){console.log(e)})},handleGetSwiperSucc:function(e){this.closeFullScreen(this.openFullScreen()),200==e.data.status&&(this.swiperList=e.data.data)}}},b={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("div",{staticClass:"swiperWrap"},[t("el-carousel",{attrs:{height:this.bannerH+"px",width:this.bannerW+"px",autoplay:!1}},this._l(this.swiperList,function(e){return t("el-carousel-item",{key:e.id},[t("router-link",{attrs:{to:"/"}},[t("img",{staticClass:"imgBox",staticStyle:{"border-radius":"0.3rem"},attrs:{src:e.pic,width:"100%",height:"100%","object-fit":"cover"}})])],1)}),1)],1)])},staticRenderFns:[]};var S=n("VU/8")(v,b,!1,function(e){n("etd+")},"data-v-769cee70",null).exports,w={created:function(){this.one=this.$router.history.current.query.one,this.two=this.$router.history.current.query.two,this.getDifferent()},data:function(){return{one:"",two:"",differentList:[]}},methods:{openFullScreen:function(){return this.$loading({lock:!0,text:"Loading",spinner:"el-icon-loading",background:"rgba(0,0,0,0.7)"})},closeFullScreen:function(e){e.close()},getDifferent:function(){this.openFullScreen();var e=new FormData;e.append("one",this.one),e.append("two",this.two),d()({method:"post",url:"/lightspace/priceclient/contrast",data:e}).then(this.handleGetDiffrentSucc.bind(this)).catch(function(e){console.log(e)})},handleGetDiffrentSucc:function(e){this.closeFullScreen(this.openFullScreen()),e.data.data&&(this.differentList=e.data.data)}}},_={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("el-row",[n("el-col",{attrs:{span:6,offset:9}},[n("div",{staticClass:"compare"},[e._v("价格对比")])])],1),e._v(" "),n("div",{staticClass:"imgBox"},e._l(e.differentList,function(t,i){return n("div",{key:i,staticClass:"itemBox"},[n("el-image",{staticStyle:{"border-radius":".2rem"},attrs:{src:t.pic,fit:"contain"}}),e._v(" "),n("div",{staticClass:"title"},[e._v(e._s(t.seriesName))])],1)}),0)],1)},staticRenderFns:[]};var y=n("VU/8")(w,_,!1,function(e){n("r2vA")},"data-v-242845e8",null).exports;i.default.use(c.a);var k=new c.a({routes:[{path:"/",redirect:"/singleFocus"},{path:"/detail",component:S},{path:"/different",component:y},{path:"/",component:f,children:[{path:"/singleFocus",component:g},{path:"/life",component:g},{path:"/protect",component:g},{path:"/changeFocus",component:g},{path:"/changeColor",component:g},{path:"/teenagers",component:g},{path:"/sunshine",component:g}]}]}),F=n("NYxO");i.default.use(F.a);var C=new F.a.Store({state:{labelId:""},mutations:{changeLabel:function(e,t){e.labelId=t}}}),L=n("v5o6"),x=n.n(L);n("tvR6"),n("+/TY"),n("VC9Y");i.default.use(r.a),i.default.config.productionTip=!1,x.a.attach(document.body),new i.default({el:"#app",router:k,components:{App:o},template:"<App/>",store:C})},VC9Y:function(e,t){},bojw:function(e,t){},"etd+":function(e,t){},"lI/0":function(e,t){},r2vA:function(e,t){},tvR6:function(e,t){},ymcE:function(e,t){}},["NHnr"]);
//# sourceMappingURL=app.69fbc475b9cd5a15a30d.js.map