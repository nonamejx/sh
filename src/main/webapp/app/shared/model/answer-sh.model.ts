export interface IAnswerSh {
    id?: number;
    title?: string;
    correctAnswer?: boolean;
    questionTitle?: string;
    questionId?: number;
}

export class AnswerSh implements IAnswerSh {
    constructor(
        public id?: number,
        public title?: string,
        public correctAnswer?: boolean,
        public questionTitle?: string,
        public questionId?: number
    ) {
        this.correctAnswer = false;
    }
}
