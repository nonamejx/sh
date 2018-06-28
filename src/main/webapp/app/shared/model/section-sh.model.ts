import { IQuestionSh } from 'app/shared/model//question-sh.model';

export interface ISectionSh {
    id?: number;
    text?: string;
    audioName?: string;
    imageName?: string;
    imageTag?: string;
    partNumber?: number;
    questions?: IQuestionSh[];
}

export class SectionSh implements ISectionSh {
    constructor(
        public id?: number,
        public text?: string,
        public audioName?: string,
        public imageName?: string,
        public imageTag?: string,
        public partNumber?: number,
        public questions?: IQuestionSh[]
    ) {}
}
