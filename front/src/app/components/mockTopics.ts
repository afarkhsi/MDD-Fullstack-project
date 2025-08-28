// data/mockArticles.ts
import { Topic } from '../interfaces/topic.interface';

export const mockTopics: Topic[] = [
    {
        name: 'Technologie',
        description: 'Tout sur les innovations tech',
        isSubscribed: false,
        id: 101,
    },
    {
        id: 102,
        name: 'Environnement',
        description: 'Développement durable et écologie',
        isSubscribed: true,
    },
    {
        id: 103,
        name: 'Santé',
        description: 'Innovations médicales et bien-être',
        isSubscribed: false,
    },

    {
            id:  104,
            name: 'Économie',
            description: 'Tendances économiques et business durable',
            isSubscribed: true,
    },
];
