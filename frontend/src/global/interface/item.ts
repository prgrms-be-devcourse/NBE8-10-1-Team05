export interface Item {
  id: number;
  category: string;
  name: string;
  price: number;
  imageUrl: string;
}

export interface PurchaseItem extends Item {
  quantity: number;
}