import { IAnswerSh } from 'app/shared/model//answer-sh.model';

export interface IQuestionSh {
    id?: number;
    title?: string;
    answers?: IAnswerSh[];
    sectionText?: string;
    sectionId?: number;
}

export class QuestionSh implements IQuestionSh {
    constructor(
        public id?: number,
        public title?: string,
        public answers?: IAnswerSh[],
        public sectionText?: string,
        public sectionId?: number
    ) {}
}
