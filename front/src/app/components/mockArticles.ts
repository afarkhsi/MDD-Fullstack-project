import { Article } from 'src/app/interfaces/article.interface';

export const mockArticles: Article[] = [
  {
    id: 1,
    title: 'L’intelligence artificielle en 2025',
    description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam.',
    createdAt: new Date('2025-08-25'),
    authorUsername: {
      name: 'Alice Dupont',
      email: 'alice.dupont@example.com'
    },
    topic: {
      id: 101,
      name: 'Technologie',
      description: 'Tout sur les innovations tech',
      isSubscribed: false,
    }
  },
  {
    id: 2,
    title: 'L’écologie urbaine',
    description: 'Suspendisse potenti. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta.',
    createdAt: new Date('2025-08-20'),
    authorUsername: {
      name: 'Karim Benali',
      email: 'karim.benali@example.com'
    },
    topic: {
      id: 102,
      name: 'Environnement',
      description: 'Développement durable et écologie',
      isSubscribed: true,
    }
  },
  {
    id: 3,
    title: 'La médecine personnalisée',
    description: 'Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor.',
    createdAt: new Date('2025-08-15'),
    authorUsername: {
      name: 'Sophie Morel',
      email: 'sophie.morel@example.com'
    },
    topic: {
      id: 103,
      name: 'Santé',
      description: 'Innovations médicales et bien-être',
      isSubscribed: false,
    }
  },
  {
    id: 4,
    title: 'Économie circulaire : un nouveau modèle',
    description: 'Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra. In dui magna, posuere eget, vestibulum et, tempor auctor, justo. In ac felis quis tortor malesuada pretium.',
    createdAt: new Date('2025-08-10'),
    authorUsername: {
      name: 'Jean-Pierre Lemoine',
      email: 'jp.lemoine@example.com'
    },
    topic: {
      id: 104,
      name: 'Économie',
      description: 'Tendances économiques et business durable',
      isSubscribed: true,
    }
  }
];
