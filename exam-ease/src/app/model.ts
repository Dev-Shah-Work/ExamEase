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
  answerText: String;
  answer: Option;
  isMcq: Boolean;
}

export interface Quiz {
  difficulty: String;
  duration: Number;
  subcategory: SubcategoryId;
  quizName:String
  user: UserId;
  tests: any;
  questions: Question[];
}
export interface Response {
  score: Number;
  responseText: String;
  responseId: {
    id:Number
  };
  questionId:Number;
}
export interface Test {
  testScore: Number;
  attemptTime: Number;
  completionTime: Number;
  responses: any;
  quizTakerId: Number;
  quizId: Number;
}
