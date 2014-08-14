package com.sales.weixin.enumeration;

/**
 *
 * @author nelson
 */
public enum MessageTypeEnum {

    text {
        @Override
        public String toString() {
            return "text";
        }

        public int getCode() {
            return 0;
        }
    }, audio {
        @Override
        public String toString() {
            return "audio";
        }
        public int getCode() {
            return 1;
        }
    }, music {
        @Override
        public String toString() {
            return "music";
        }
        public int getCode() {
            return 2;
        }
    }, vedio {
        @Override
        public String toString() {
            return "vedio";
        }
        public int getCode() {
            return 3;
        }
    }, news {
        @Override
        public String toString() {
            return "news";
        }
        public int getCode() {
            return 4;
        }
    }, image {
        @Override
        public String toString() {
            return "image";
        }
        public int getCode() {
            return 5;
        }
    }, location {
        @Override
        public String toString() {
            return "location";
        }

        public int getCode() {
            return 6;
        }
    }, link {
        @Override
        public String toString() {
            return "link";
        }
        public int getCode() {
            return 7;
        }
    }, event_event {
        @Override
        public String toString() {
            return "event";
        }
        public int getCode() {
            return 8;
        }
    }, event_scan {
        @Override
        public String toString() {
            return "scan";
        }
        public int getCode() {
            return 9;
        }
    }, event_subscribe {
        @Override
        public String toString() {
            return "subscribe";
        }
        public int getCode() {
            return 10;
        }
    },event_unsubscribe {
        @Override
        public String toString() {
            return "unsubscribe";
        }
        public int getCode() {
            return 11;
        }
    }
}
