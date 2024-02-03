package com.charlymech.anyteeth.Enums;

import static com.charlymech.anyteeth.App.rb;

public enum MaritalStatus {
	SINGLE {
		public String toString() {
			return rb.getString("maritalStatusSingle");
		}
	},
	MARRIED_JOINTLY {
		public String toString() {
			return rb.getString("maritalStatusMarriedJointly");
		}
	},
	MARRIED_SEPARATELY {
		public String toString() {
			return rb.getString("maritalStatusMarriedSeparately");
		}
	},
	HEAD_FAMILY {
		public String toString() {
			return rb.getString("maritalStatusHeadFamily");
		}
	},
	WIDOWER_DEPENDENT_CHILD {
		public String toString() {
			return rb.getString("maritalStatusWidowerDependentChild");
		}
	}
}
