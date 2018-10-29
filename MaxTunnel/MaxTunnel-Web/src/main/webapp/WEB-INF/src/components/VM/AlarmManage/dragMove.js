import { on,off } from "../../../scripts/commonFun";

// 模态框拖拽移动
export default {
    data () {
        return {
            dragData: {
                x: null,
                y: null,
                dragX: null,
                dragY: null,
                dragging: false
            },
            mapRect:null,
            rect:null,
        };
    },
    watch:{

    },
    computed:{
        mainStyles () {
            let style = {};

            const width = parseInt(this.width);
            const styleWidth = this.dragData.x !== null ? {
                top: 0
            } : {
                width: width <= 100 ? `${width}%` : `${width}px`
            };

            Object.assign(style, styleWidth);

            return style;
        },
        contentStyles () {
            let style = {};

            if (this.draggable) {
                if (this.dragData.x !== null) style.left = `${this.dragData.x}px`;
                if (this.dragData.y !== null) style.top = `${this.dragData.y}px`;
            }
            const width = parseInt(this.width);
            const styleWidth = {
                width: width <= 100 ? `${width}%` : `${width}px`
            };
            Object.assign(style, styleWidth);
            return style;
        },
    },
    methods:{
        handleMoveStart (event) {
            if (!this.draggable) return false;

            const $content = this.$refs.content;
            this.rect = $content.getBoundingClientRect();

            this.mapRect = this.getDOMElement('Map').getBoundingClientRect();

            this.dragData.x = this.rect.x;
            this.dragData.y = this.rect.y;

            const distance = this.distance(event);

            this.dragData.dragX = distance.x;
            this.dragData.dragY = distance.y;

            this.dragData.dragging = true;

            on(window, 'mousemove', this.handleMoveMove);
            on(window, 'mouseup', this.handleMoveEnd);
        },
        handleMoveMove (event) {
            if (!this.dragData.dragging) return false;

            const distance = this.distance(event);

            const diff_distance = {
                x: distance.x - this.dragData.dragX,
                y: distance.y - this.dragData.dragY
            };

            const x = ( this.dragData.x + diff_distance.x ) - this.rect.width / 2;
            const y = ( this.dragData.y + diff_distance.y ) - this.mapRect.y;

            this.getDOMElement('des-model').style.left = `${x}px`;
            this.getDOMElement('des-model').style.top = `${y}px`;

        },
        handleMoveEnd () {
            this.dragData.dragging = false;

            off(window, 'mousemove', this.handleMoveMove);
            off(window, 'mouseup', this.handleMoveEnd);
        },
        distance(event){

            return {
                x: event.clientX,
                y: event.clientY
            }
        }
    }
}