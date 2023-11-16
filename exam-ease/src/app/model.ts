export default interface AppUser {
  firstname: String;
  lastname: String;
  email: String;
  password: String;
  phoneNo: String;
  role: String;
}
interface SubcategoryId {
  id: Number;
}
interface UserId {
  id: Number;
}
interface Option {
  optionText: String;
}
interface Question {
  point: Number;
  questionText: String;
  img: any;
  options: Option[];
  responses: any;
  answer: Option;
  mcq: Boolean;
}

export default interface Quiz {
  difficulty: String;
  duration: Number;
  subcategory: SubcategoryId;
  user: UserId;
  tests: any;
  questions: Question[];
}
