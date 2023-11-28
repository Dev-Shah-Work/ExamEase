export default interface AppUser {
  firstname: String;
  lastname: String;
  email: String;
  password: String;
  phoneNo: String;
  role: String;
}
export interface SubcategoryId {
  id: Number;
}
export interface UserId {
  id: Number;
}
export interface Option {
  optionText: String;
}
export interface Question {
  point: Number;
  questionText: String;
  img: any;
  options: Option[];
  responses: any;
  answerText:String;
  answer: Option;
  isMcq: Boolean;
}

export  interface Quiz {
  difficulty: String;
  duration: Number;
  subcategory: SubcategoryId;
  user: UserId;
  tests: any;
  questions: Question[];
}
