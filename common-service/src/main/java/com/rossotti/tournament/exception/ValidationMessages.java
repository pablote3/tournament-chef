package com.rossotti.tournament.exception;

public interface ValidationMessages {
	String MSG_VAL_0000 = "Unexpected Error occurred during process flow. (MSG_VAL_0000)";
	String MSG_VAL_0001 = "Missing value for {0}. (MSG_VAL_0001)";
	String MSG_VAL_0002 = "Required value of {0} was not provided. (MSG_VAL_0002)";
	String MSG_VAL_0003 = "Value for {0} is supposed to be a {1} but was not - value was  {2}. (MSG_VAL_0003)";
	String MSG_VAL_0004 = "Acceptable value for {0} was not supplied - value for this {1} was {2}.  (MSG_VAL_0004)";
	String MSG_VAL_0005 = "Value for {0} is too long - length for {1} is {2}. (MSG_VAL_0005)";
	String MSG_VAL_0006 = "Unable to locate record with passed in id : {0}  (MSG_VAL_0006)";
	String MSG_VAL_0007 = "Server error when trying to delete record (MSG_VAL_0007)";
	String MSG_VAL_0008 = "Server error when trying to save record (MSG_VAL_0008)";
	String MSG_VAL_0009 = "The supplied value {0} for field {1} requires a {2} digit length. (MSG_VAL_0009)";
	String MSG_VAL_0010 = "The supplied value {0} is greater than the maximum value of {1}. (MSG_VAL_0010)";
	String MSG_VAL_0011 = "The supplied value {0} is less than the minimum value of {1}. (MSG_VAL_0011)";
	String MSG_VAL_0012 = "Server error when trying to find record for id of {} (MSG_VAL_0012)";
	String MSG_VAL_0013 = "Server error occurred during search(MSG_VAL_0013)";
	String MSG_VAL_0014 = "Unable to locate records with passed search criteria. (MSG_VAL_0014)";
	String MSG_VAL_0015 = "Error prepping and creating response object. (MSG_VAL_0015)";
}
