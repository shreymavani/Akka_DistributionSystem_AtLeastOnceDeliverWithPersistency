package org.example;

import java.io.Serializable;


interface Message {
    class Msg implements Serializable {
        private static final long serialVersionUID = 1L;
        public final long deliveryId;
        public final String s;

        public Msg(long deliveryId, String s) {
            this.deliveryId = deliveryId;
            this.s = s;
        }
    }

    class Confirm implements Serializable {
        private static final long serialVersionUID = 1L;
        public final long deliveryId;

        public Confirm(long deliveryId) {
            this.deliveryId = deliveryId;
        }
    }

    class MsgSent implements Serializable {
        private static final long serialVersionUID = 1L;
        public final String s;

        public MsgSent(String s) {
            this.s = s;
        }
    }

    class MsgConfirmed implements Serializable {
        private static final long serialVersionUID = 1L;
        public final long deliveryId;

        public MsgConfirmed(long deliveryId) {
            this.deliveryId = deliveryId;
        }
    }
}